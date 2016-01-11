package com.example


import scala.beans.BeanProperty

/**
 * Created by caleb on 1/10/16.
 */
case class Contact(
  @BeanProperty var firstName: String = "",
  @BeanProperty var lastName: String = "",
  @BeanProperty var phone: String = "",
  @BeanProperty var email: String = ""
)
