package controllers

import models.{Token, TokenDao}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}

class TokenController extends Controller with TokenDao {

  import driver.api._

  val tokenForm = Form(
    mapping(
      "user_id" -> number(min = 0)
    )((userId) => Token(userId))
      ((t: Token) => Some(t.userId))
  )

  def index = Action.async {
    //    db.run(tokenDAO.result).map(res => Ok(views.html.tokens.index(res.toList)))
    allToken.map(c => Ok(views.html.tokens.index(c)))
  }

  def insert = Action.async { implicit rs =>
    tokenForm.bindFromRequest.fold(
      formWithErrors => {
        // Create async to return detail error
        val futureString = scala.concurrent.Future {
          formWithErrors.toString
        }
        futureString.map(e => BadRequest(e))
      },
      token => {
        db.run(tokenDAO += token).map(_ => Redirect(routes.TokenController.index))
        //        insertToken(token).map(_ => Redirect(routes.TokenController.index))
      }
    )
  }

  def validate(token: String) = Action.async {
    
    isValid(token).map(c => Ok(c.toString))
  }

}