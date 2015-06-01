package dao

import slick.driver.JdbcProfile
import models.User

/**
 * Created by TriLe on 05/29/15.
 */
trait UserTable {

  protected val driver: JdbcProfile

  import driver.api._
  import java.util.Date


  class Users(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def firstName = column[String]("first_name")

    def lastName = column[String]("last_name")

    def username = column[String]("username")

    def passHash = column[String]("pass_hash")

    def joinDate = column[Date]("join_date")

    def avatarLink = column[String]("avatar_link")


    def * = (id.?, firstName, lastName, username, passHash, joinDate, avatarLink) <>(User.tupled, User.unapply)
  }

  val userDAO = TableQuery[Users]
}