package com.example

import vaadin.scala._
import vaadin.scala.server.ScaladinRequest

/**
 * Created by caleb on 1/9/16.
 */
class AddressbookUI extends UI(theme = ValoTheme.ThemeName) {
  val filter = new TextField()
  val contactList = new Grid()
  val newContact = Button("New contact")

  // ContactForm is an example of a custom component class
  val contactForm = new ContactForm()

  // ContactService is a in-memory mock DAO that mimics
  // a real-world datasource. Typically implemented for
  // example as EJB or Spring Data based service.
  val service = ContactService.createDemoService()


  /* The "Main method".
   *
   * This is the entry point method executed to initialize and configure
   * the visible user interface. Executed on every browser reload because
   * a new instance is created for each web page loaded.
   */
  override def init(request: ScaladinRequest): Unit = {
    configureComponents()
    buildLayout()
  }

  private def configureComponents() = {
     /* Synchronous event handling.
     *
     * Receive user interaction events on the server-side. This allows you
     * to synchronously handle those events. Vaadin automatically sends
     * only the needed changes to the web page without loading a new page.
     */
    newContact.clickListeners += {contactForm.edit(Option(new Contact()))}

    filter.prompt = "Filter contacts..."
    filter.textChangeListeners += {e => refreshContacts(e.text)}

    contactList.container = new BeanItemContainer[Contact]()
    contactList.selectionMode = SelectionMode.Single
    contactList.selectionListeners += {
      val c = contactList.selectedRow.map(x => x.asInstanceOf[Contact])
      contactForm.edit(c)
    }
    refreshContacts()
  }

  /* Robust layouts.
   *
   * Layouts are components that contain other components.
   * HorizontalLayout contains TextField and Button. It is wrapped
   * with a Grid into VerticalLayout for the left side of the screen.
   * Allow user to resize the components with a SplitPanel.
   *
   * In addition to programmatically building layout in Java,
   * you may also choose to setup layout declaratively
   * with Vaadin Designer, CSS and HTML.
   */
  private def buildLayout() = {
    val actions = new HorizontalLayout {
      add(filter)
      add(newContact)
      width = 100.percent
      setExpandRatio(filter, 1)
    }
    filter.width = 100.percent

    val left = VerticalLayout.fullSized(actions, contactList)
    contactList.sizeFull()
    left.setExpandRatio(contactList, 1)

    val mainLayout = new HorizontalLayout { layout =>
      add(left)
      add(contactForm)
      sizeFull()
      setExpandRatio(left, 1)
    }

    // Split and allow resizing
    this.content = mainLayout
  }

  /* Choose the design patterns you like.
   *
   * It is good practice to have separate data access methods that
   * handle the back-end access and/or the user interface updates.
   * You can further split your code into classes to easier maintenance.
   * With Vaadin you can follow MVC, MVP or any other design pattern
   * you choose.
   */
  def refreshContacts(): Unit = {
    refreshContacts(filter.value.getOrElse(""))
  }

  private def refreshContacts(stringFilter: String): Unit = {
    val contacts = service.findAll(stringFilter)
    contactList.container = new BeanItemContainer[Contact](contacts)
    contactForm.visible = false
  }
}
