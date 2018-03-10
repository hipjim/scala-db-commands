# scala-db-commands
Scala JDBC library

Project aims to provide a minimal library for executing SQL code from a Scala program.
No external dependencies, no strange ORMs constructs, just simple code that gets the job done in a safley manner.

```scala
implicit val conn = DriverManager.getConnection("jdbc:h2:mem:test")

query("select id, name, surname from user where id = 1") { rs =>
      User(rs.getInt(1), rs.getString(2))
    } match {
      case Success(users) => assert(users.head.id == 1)
      case Failure(ex) => handleFailure(ex)
    }
```