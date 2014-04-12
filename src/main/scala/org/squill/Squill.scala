package org.squill

import javax.sql.DataSource
import java.sql.{PreparedStatement, Connection, ResultSet}
import scala.annotation.implicitNotFound

/**
 * Created by cristipopovici on 3/24/14.
 */
object Squill extends Transaction with Query with Update {

  @implicitNotFound("implicit javax.sql.DataSource not found")
  override def tx[T](txBlock: => T)(implicit dataSource: DataSource): T = {
    require(dataSource != null, "data source cannot be null")
    val connection = dataSource.getConnection
    WithTransaction(txBlock)(connection)
  }

  def query[T](sqlStatement: String, params: Any*)(f: ResultSet => T)(implicit connection: Connection): Seq[T] = {
    var ps = Option.empty[PreparedStatement]
    var rs = Option.empty[ResultSet]
    try {
      ps = Some(connection.prepareStatement(sqlStatement))
      ps.map(_.executeQuery()).map {
        r => {
          rs = Some(r)
          ResultSetIterator(r).map(f).toList
        }
      } getOrElse Seq.empty[T]
    } finally {
      rs.map(_.close())
      ps.map(_.close())
    }
  }

  def update(sqlStatement: String, params: Any*)(implicit connection: Connection): Int = {
    var ps = Option.empty[PreparedStatement]
    try {
      ps = Some(connection.prepareStatement(sqlStatement))
      ps.map(_.executeUpdate()).get
    } finally {
      ps.map(_.close())
    }
  }
}

private[this] case class ResultSetIterator(rs: ResultSet) extends Iterator[ResultSet] {
  override def next(): ResultSet = rs
  override def hasNext: Boolean = rs.next()
}


