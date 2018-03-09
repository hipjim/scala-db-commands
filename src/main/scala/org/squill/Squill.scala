package org.squill

import java.sql.{Connection, PreparedStatement, ResultSet}
import javax.sql.DataSource

import scala.annotation.implicitNotFound
import scala.util.Try

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

  def query[T](sql: String, params: Any*)(f: ResultSet => T)(implicit connection: Connection): Try[Seq[T]] =
    Try {
      val ps: PreparedStatement = connection.prepareStatement(sql)
      val rs: ResultSet = ps.executeQuery()
      ResultSetIterator(rs).map(f).toSeq
    }

  def update(sqlStatement: String, params: Any*)(
    implicit connection: Connection): Int = {
    var ps = Option.empty[PreparedStatement]
    try {
      ps = Some(connection.prepareStatement(sqlStatement))
      ps.map(_.executeUpdate()).get
    } finally {
      ps.foreach(_.close())
    }
  }
}

private[this] case class ResultSetIterator(rs: ResultSet)
  extends Iterator[ResultSet] {
  override def next(): ResultSet = rs

  override def hasNext: Boolean = rs.next()
}
