package controllers

import models.User
import dao.UserTable
import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.mvc.{Action, Controller}
import slick.driver.JdbcProfile

class UserController extends Controller with UserTable with HasDatabaseConfig[JdbcProfile] {

  import driver.api._

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)


  def index = Action.async {
    db.run(userDAO.result).map(res => Ok("OKe"))
  }

  val userForm = Form(
    tuple(
      "first_name" -> text,
      "last_name" -> text,
      "username" -> text,
      "password" -> text,
      "avatar_link" -> text
    )
  )

  def insert = Action.async { implicit rs =>
    val (firstName, lastName, username, password, avatarLink) = userForm.bindFromRequest.get
    val user = new  User(firstName, lastName, username,password.reverse, avatarLink
    db.run(userDAO += user).map(_ => Redirect(routes.Application.index))
  }
}