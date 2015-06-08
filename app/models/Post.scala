package models

import java.sql.Timestamp

import slick.driver.PostgresDriver.api._

/**
 * Created by TriLe on 06/03/15.
 */
case class Post(
                 // Id of user who owner of post
                 userId: Int,
                 // Title
                 title: String,
                 // Description
                 description: String,
                 // Content
                 content: String,
                 // Create time: get current time
                 createTime: Timestamp,
                 // Edit time: time when user edit, if user not edit yet then value is None
                 editTime: Option[Timestamp] = None,
                 // Edit time: time when user public comment, if user not public yet then value is None
                 publicTime: Option[Timestamp] = None,
                 // Id of post
                 id: Option[Int] = None)

class Posts(tag: Tag) extends Table[Post](tag, "posts") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def userId = column[Int]("user_id")

  def title = column[String]("title")

  def description = column[String]("description")

  def content = column[String]("content")

  def createTime = column[Timestamp]("create_time")

  def editTime = column[Option[Timestamp]]("edit_time")

  def publicTime = column[Option[Timestamp]]("public_time")

  def * = (userId, title, description, content, createTime, editTime, publicTime, id.?) <>(Post.tupled, Post.unapply)
}

