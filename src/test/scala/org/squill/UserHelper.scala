package org.squill

trait UserHelper extends H2 {

  def prepare = {
    createTable
    truncateTable
    insertUser
  }

  def createTable = conn.createStatement().execute("CREATE TABLE IF NOT EXISTS user (id INT, name VARCHAR(50), surname VARCHAR(50))")

  def insertUser = conn.createStatement().execute("INSERT INTO user VALUES(1, 'elvis', 'testing')")

  def truncateTable = conn.createStatement().execute("TRUNCATE TABLE user")

  case class User(id: Int, name: String, surname: String)

}
