package models

import java.sql.Date

import slick.driver.PostgresDriver.api._

/**
 * Created by TriLe on 06/01/15.
 */
case class User(
                 // First name
                 firstName: String,
                 // Last name
                 lastName: String,
                 // Username
                 username: String,
                 // Password of user, if get from form then original pw, if get from BD the pass hashed
                 pass: String,
                 // Link to avatar image
                 avatarLink: Option[String] = None,
                 // Join date: get current date
                 joinDate: Date = new Date(System.currentTimeMillis),
                 // Id of user
                 id: Option[Int] = None)

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
