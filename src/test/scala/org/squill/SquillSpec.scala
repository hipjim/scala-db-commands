package org.squill

import org.scalatest._
import org.squill.Squill._

import scala.util.{Failure, Success}

class SquillSpec extends FlatSpec with Matchers with H2 with UserHelper {

  "a user correct select query " should " return the user" in {
    prepare
    query("select id, name, surname from user where id = 1") { rs =>
      User(rs.getInt(1), rs.getString(2), rs.getString(3))
    } match {
      case Success(users) => assert(users.head.id == 1)
      case Failure(ex) => ex.printStackTrace()
    }
  }

  "a user incorrect select query " should " throw exception " in {
    prepare
    assertThrows[org.h2.jdbc.JdbcSQLException](
      query("select id, name, surname from use where id = 1") { rs =>
        User(rs.getInt(1), rs.getString(2), rs.getString(3))
      }.get
    )
  }

  "a user correct results query " should " return the user" in {
    prepare
    val users = results("select id, name, surname from user where id = 1") { rs =>
      User(rs.getInt(1), rs.getString(2), rs.getString(3))
    }.head
    assert(users.id == 1)
  }

  "a user incorrect results query " should " return empty sequence" in {
    prepare
    val users = results("select id, name, surname from use where id = 1") { rs =>
      User(rs.getInt(1), rs.getString(2), rs.getString(3))
    }
    assert(users.isEmpty)
  }
}
