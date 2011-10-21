/**
Used as a convenient helper when inserting into a join-table.
Constructors for other types are supposed to be added if needed.
*/
package lards.model.dto

import scala.reflect.BeanProperty
import java.util.Collection
import collection.JavaConversions._
import java.sql.Timestamp
import lards.global.Now


class N_to_m_join(val a_id: java.lang.Long = -1,
  val b_id: java.lang.Long = -1,
  val timestamp: java.sql.Timestamp = Now.timestamp) {

  def this(user: User, location: Location) = this(user.id, location.id, user.timestamp)
}
