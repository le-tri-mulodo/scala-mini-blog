package controllers

import dao.CatTable
import models.Cat
import play.api.Play
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
import slick.driver.JdbcProfile

class Application extends Controller with CatTable with HasDatabaseConfig[JdbcProfile] {

  import driver.api._

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)


  def index = Action.async {
    db.run(catDao.result).map(res => Ok(views.html.index(res.toList)))
  }

  val catForm = Form(
    mapping(
      "name" -> text(),
      "color" -> text()
    )(Cat.apply)(Cat.unapply)
  )

  def insert = Action.async { implicit rs =>
    val cat = catForm.bindFromRequest.get
    db.run(catDao += cat).map(_ => Redirect(routes.Application.index))
  }
}