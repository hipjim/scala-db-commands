package org.squill

import java.sql.{Connection, ResultSet}

import scala.annotation.implicitNotFound
import scala.util.{Failure, Success, Try}

/**
  * Created by cristipopovici on 3/24/14.
  */
sealed trait SqlCommand

trait Query extends SqlCommand {
  @implicitNotFound("implicit sql java.sql.Connection not found")
  def query[T](sqlStatement: String, params: Any*)(rs: ResultSet => T)(implicit connection: Connection): Try[Seq[T]]

  def results[T](sqlStatement: String, params: Any*)(rs: ResultSet => T)(implicit connection: Connection): Seq[T] =
    query(sqlStatement, params)(rs) match {
      case Success(res) => res
      case Failure(ex) => {
        //TODO: add logging and correct logging level for errors
        ex.printStackTrace()
        Seq.empty[T]
      }
    }
}

trait Update extends SqlCommand {
  @implicitNotFound("implicit sql java.sql.Connection not found")
  def update(sqlStatement: String, params: Any*)(implicit connection: Connection): Int
}

