package models

import java.sql.Timestamp

import play.api.Play.current
import play.api.libs.Crypto
import slick.driver.PostgresDriver.api._

/**
 * Created by TriLe on 06/02/15.
 */
case class Token(
                  // User Id refer to User table
                  userId: Int,
                  // value of token. auto-gen when create instalse
                  value: String = Crypto.generateToken,
                  // create time: get current time
                  createTime: Timestamp = new Timestamp(System.currentTimeMillis),
                  // expired time: get current time and add more (get from config)
                  expiredTime: Timestamp = new Timestamp(System.currentTimeMillis
                    + current.configuration.getLong("token.expired.time ").get),
                  // id of token
                  id: Option[Int] = None)

class Tokens(tag: Tag) extends Table[Token](tag, "tokens") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def userId = column[Int]("user_id")

  def value = column[String]("value")

  def createTime = column[Timestamp]("create_time")

  def expiredTime = column[Timestamp]("expired_time")

  def * = (userId, value, createTime, expiredTime, id.?) <>(Token.tupled, Token.unapply)
}
