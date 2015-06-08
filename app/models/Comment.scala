package models

import java.sql.Timestamp

import slick.driver.PostgresDriver.api._

/**
 * Created by TriLe on 06/03/15.
 */
case class Comment(
                    // Id of user which owner of comment
                    userId: Int,
                    // Id of post which contain comment
                    postId: Int,
                    // Content
                    content: String,
                    // Create time: get current time
                    createTime: Timestamp,
                    // Edit time: time when user edit, if user not edit yet then value is None
                    editTime: Option[Timestamp] = None,
                    // Id of parent comment which parent of current comment
                    pCommentId: Option[Int],
                    // Id of comment
                    id: Option[Int])

class Comments(tag: Tag) extends Table[Comment](tag, "comments") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def userId = column[Int]("user_id")

  def postId = column[Int]("post_id")

  def content = column[String]("content")

  def createTime = column[Timestamp]("create_time")

  def editTime = column[Option[Timestamp]]("edit_time")

  def pCommentId = column[Option[Int]]("pcomment_id")

  def * = (userId, postId, content, createTime, editTime, pCommentId, id.?) <>(Comment.tupled, Comment.unapply)
}
