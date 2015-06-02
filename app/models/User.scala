package models

import java.sql.Date

/**
 * Created by TriLe on 06/01/15.
 */
case class User(firstName: String,
                lastName: String,
                username: String,
                passHash: String,
                avatarLink: Option[String] = None,
                joinDate: Date = new Date(System.currentTimeMillis),
                id: Option[Int] = None)

trait UserTable {

  import slick.driver.JdbcProfile

  protected val driver: JdbcProfile

  import driver.api._

  class Users(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def firstName = column[String]("first_name")

    def lastName = column[String]("last_name")

    def username = column[String]("username")

    def passHash = column[String]("pass_hash")

    def joinDate = column[Date]("join_date")

    def avatarLink = column[Option[String]]("avatar_link")


    def * = (firstName, lastName, username, passHash, avatarLink, joinDate, id.?) <>(User.tupled, User.unapply)
  }

  val userDAO = TableQuery[Users]
}