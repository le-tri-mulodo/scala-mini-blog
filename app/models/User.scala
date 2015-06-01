package models

import java.util.Date
import java.util.Calendar

/**
 * Created by TriLe on 06/01/15.
 */
case class User(id: Option[Int],
                firstName: String,
                lastName: String,
                username: String,
                passHash: String,
                joinDate: Date,
                avatarLink: String) {

  def this(firstName: String,
           lastName: String,
           username: String,
           passHash: String,
           avatarLink: String) {

    val today = Calendar.getInstance().getTime()

    this(None, firstName, lastName, username, passHash, today, avatarLink)
  }
}
