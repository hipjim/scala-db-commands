package org.squill

import java.sql.Connection

/**
  * Created by cristipopovici on 3/24/14.
  */
private object WithTransaction {
  def apply[T](block: => T)(conn: Connection): T = {
    try {
      val value = block
      conn.commit()
      value
    } catch {
      case th: Throwable =>
        if (conn != null) {
          conn.rollback()
        }
        throw th
    } finally {
      Option(conn).foreach(_.close())
    }
  }
}
