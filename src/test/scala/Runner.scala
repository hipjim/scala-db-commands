
/**
  * Created by cristipopovici on 3/25/14.
  */
object Runner extends App {

  import org.squill.Squill._

  case class User(name: String, surname: String)

  tx {
    val results = query("select name,surname from user") { rs =>
      User(rs.getString(1), rs.getString(2))
    }

    results

    update("update user set name = ?", "cristi.popovici@gmail.com")
  }

}
