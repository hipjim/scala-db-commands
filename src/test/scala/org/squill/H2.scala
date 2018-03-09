package org.squill

import java.sql.DriverManager

trait H2 {
  Class.forName("org.h2.Driver")
  implicit val conn = DriverManager.getConnection("jdbc:h2:mem:test")
}
