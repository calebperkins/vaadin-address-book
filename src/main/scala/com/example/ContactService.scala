package com.example

import scala.collection.mutable.MutableList


/**
 * Created by caleb on 1/9/16.
 */
object ContactService {
  def createDemoService(): ContactService = {
    val x = new ContactService()
    x.save(Contact(firstName = "Caleb", lastName = "Perkins", email = "caleb_perkins@apple.com", phone = "555-123-1234"))
    x.save(Contact(firstName = "Steve", lastName = "Jobs", email = "steve@apple.com", phone = "999-123-1234"))
    x.save(Contact(firstName = "Tim", lastName = "Cook", email = "cook@apple.com", phone = "888-123-1234"))
    x
  }
}

class ContactService {
  private var contacts: MutableList[Contact] = MutableList()

  def save(contact: Contact) = {
    contacts += contact
  }

  def findAll(stringFilter: String): List[Contact] = {
    contacts.filter(c => stringFilter.isEmpty || c.firstName.contains(stringFilter)).toList
  }
}
