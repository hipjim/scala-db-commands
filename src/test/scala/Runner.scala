import java.sql.Connection

/**
  * Created by cristipopovici on 3/25/14.
  */
//TODO: delete this class, left here for now just as an usage example
object Runner extends App {

  case class User(name: String, surname: String)

  import org.squill.Squill._

  implicit val conn: Connection = null

//  val transactional = tx {
//    val result = query("select name, surname from user") { rs =>
//      User(rs.getString(1), rs.getString(2))
//    }
//
//    update("update user set name = ?", "cristi.popovici@gmail.com")
//  }
}
