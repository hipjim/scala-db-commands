package org.squill

import javax.sql.DataSource

import scala.annotation.implicitNotFound

/**
  * Created by cristipopovici on 3/24/14.
  */
trait Transaction {
  @implicitNotFound("implicit javax.sql.DataSource not found")
  def tx[T](txBlock: => T)(implicit dataSource: DataSource): T
}
