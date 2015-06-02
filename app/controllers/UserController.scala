package controllers

import models.{User, UserTable}
import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.mvc.{Action, Controller}
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits._

class UserController extends Controller with UserTable with HasDatabaseConfig[JdbcProfile] {

  import driver.api._

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)


  def index = Action.async {
    db.run(userDAO.result).map(res => Ok(views.html.users.index(res.toList)))
  }

  val userForm = Form(
    mapping(
      "first_name" -> nonEmptyText(maxLength = 64),
      "last_name" -> text,
      "username" -> text,
      "password" -> text,
      "avatar_link" -> optional(text)
    )((firstName, lastName, username, password, avatarLink) => User(firstName, lastName, username, password.reverse, avatarLink))
      ((u: User) => Some((u.firstName, u.lastName, u.username, "", u.avatarLink)))
  )

  def insert = Action.async { implicit rs =>
    val user = userForm.bindFromRequest.get
    db.run(userDAO += user).map(_ => Redirect(routes.UserController.index))
  }
}