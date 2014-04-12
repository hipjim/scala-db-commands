
/**
 * Created by cristipopovici on 3/25/14.
 */
class Runner extends App {

  import org.squill.Squill._

  case class User(name:String, surname:String)
  tx {
    query("select name,surname from user", "cristi.popovici@gmail.com") {
      rs => User(rs.getString(1), rs.getString(2))
    }

    update("update user set name = ?", "cristi.popovici@gmail.com")
  }

}
