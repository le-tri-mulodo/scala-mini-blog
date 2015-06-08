package models

import java.sql.Timestamp

import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

/**
 * Created by TriLe on 06/04/15.
 */
trait AbsDao extends HasDatabaseConfig[JdbcProfile] {

  import driver.api._

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  val tokenDAO = TableQuery[Tokens]
  val commentDAO = TableQuery[Comments]
  val postDAO = TableQuery[Posts]
  val userDAO = TableQuery[Users]
}

trait TokenDao extends AbsDao {

  import driver.api._

  def allToken() = db.run(tokenDAO.result).map(_.toList)

  def isValid(value: String) = {
    db.run(tokenDAO.filter(t => {
      t.value === value && t.expiredTime > new Timestamp(System.currentTimeMillis)
    }
    ).exists.result)
  }

  def insertToken(t: Token) = db.run(tokenDAO += t).map(_ => ())

//  def deleteToken(uId: Int) = db.run(tokenDAO.filter(_.userId === uId).delete).map(_ => ())

}