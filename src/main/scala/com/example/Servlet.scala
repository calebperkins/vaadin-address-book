package com.example

import javax.servlet.annotation.WebServlet
import vaadin.scala.server.ScaladinServlet

/**
 * Created by caleb on 1/10/16.
 */
@WebServlet(urlPatterns = Array("/*"))
class Servlet extends ScaladinServlet(
  ui = classOf[AddressbookUI]
)
