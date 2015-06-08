package controllers

import models.{AbsDao, User}
import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.{Action, Controller}
import slick.driver.JdbcProfile

class UserController extends Controller with AbsDao {

  import driver.api._

  def index = Action.async {
    db.run(userDAO.filter(x => (x.id > 3 && x.id < 8)).result).map(res => Ok(views.html.users.index(res.toList)))
  }

  val userForm = Form(
    mapping(
      "first_name" -> nonEmptyText(minLength = 2, maxLength = 5),
      "last_name" -> nonEmptyText(minLength = 2, maxLength = 5),
      "username" -> text,
      "password" -> text,
      "avatar_link" -> optional(text)
    )((firstName, lastName, username, password, avatarLink) => User(firstName, lastName, username, password.reverse,
      avatarLink))
      ((u: User) => Some((u.firstName, u.lastName, u.username, "", u.avatarLink)))
  )

  def insert = Action.async { implicit rs =>
    userForm.bindFromRequest.fold(
      formWithErrors => {
        // Create async to return detail error
        val futureString = scala.concurrent.Future {
          formWithErrors
        }
        futureString.map(e => BadRequest(views.html.users.error(e)))
      },
      user => {
        db.run(userDAO += user).map(_ => Redirect(routes.UserController.index))
      }
    )
  }
}