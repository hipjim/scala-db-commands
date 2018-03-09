package org.squill

import org.scalatest._
import org.squill.Squill._

import scala.util.{Failure, Success}

class SquillSpec extends FlatSpec with Matchers {

  case class User(id: Int, name: String, surname: String)

  "a user select query " should " return the user" in {

    //TODO: move these lines to trait (Test)MemConnection
    import java.sql.DriverManager
    Class.forName("org.h2.Driver")
    implicit val conn = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "")

    //TODO: move create table, start from default results to trait (Test)Table
    conn.createStatement().execute("CREATE TABLE user (id INT, name VARCHAR(50), surname VARCHAR(50))")
    conn.createStatement().execute("INSERT INTO user VALUES(1, 'elvis', 'testing')")


    query("select id, name, surname from user where id = 1") { rs =>
      User(rs.getInt(1), rs.getString(2), rs.getString(3))
    } match {
      case Success(users) => assert(users.head.id == 1)
      case Failure(ex) => ex.printStackTrace()
    }

    assertThrows[org.h2.jdbc.JdbcSQLException](
      query("select id, name, surname from use where id = 1") { rs =>
        User(rs.getInt(1), rs.getString(2), rs.getString(3))
      }.get
    )

    val users1 = results("select id, name, surname from user where id = 1") { rs =>
      User(rs.getInt(1), rs.getString(2), rs.getString(3))
    }.head
    assert(users1.id == 1)

    val users2 = results("select id, name, surname from use where id = 1") { rs =>
      User(rs.getInt(1), rs.getString(2), rs.getString(3))
    }
    assert(users2.isEmpty)
  }
}
