package com.example

import vaadin.scala._

/**
 * Created by caleb on 1/9/16.
 */
class ContactForm extends FormLayout {
  val saveButton = Button("save", save)
  val cancelButton = Button("Cancel", cancel)

  val firstName = new TextField {
    caption = "First name"
  }
  val lastName = new TextField {
    caption = "Last name"
  }
  val phone = new TextField {
    caption = "Phone"
  }
  val email = new TextField {
    caption = "Email"
  }

  var contact: Contact = null

  var formFieldBindings: FieldGroup = null

  configureComponents()
  buildLayout()

  private def configureComponents() = {
    saveButton.styleName = ValoTheme.ButtonPrimary
    saveButton.clickShortcut = KeyShortcut(KeyCode.Enter)
    visible = false
  }

  private def buildLayout() = {
    sizeUndefined()
    margin = true

    val actions = new HorizontalLayout { layout =>
      add(saveButton)
      add(cancelButton)
      spacing = true
    }

    add(actions)
    add(firstName)
    add(lastName)
    add(phone)
    add(email)
  }

  def save(): Unit = {
    if (contact == null) return

    // Commit the fields from UI to DAO
    formFieldBindings.commit()

    // Save DAO to backend with direct synchronous service API
    ui.service.save(contact)

    val msg = s"Saved ${contact.firstName} ${contact.lastName}"
    Notification.show(msg, Notification.Type.Tray)
    ui.refreshContacts()
  }

  def cancel(): Unit = {
    // Place to call business logic.
    Notification.show("Cancelled", Notification.Type.Tray)
    ui.contactList.select(null)
  }

  def edit(contact: Option[Contact]) = {
    this.contact = contact.orNull
    contact.foreach { c =>
      formFieldBindings = FieldGroup(new BeanItem[Contact](c))
      formFieldBindings.bind(firstName, "firstName")
      formFieldBindings.bind(lastName, "lastName")
      formFieldBindings.bind(phone, "phone")
      formFieldBindings.bind(email, "email")
      firstName.focus()
    }
    visible = contact.isDefined
  }

  override def ui: AddressbookUI = super.ui.asInstanceOf[AddressbookUI]
}
