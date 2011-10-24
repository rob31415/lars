/**
Used as a convenient helper when inserting into a join-table.
Constructors for other types are supposed to be added when needed.
*/
package lards.model.dto

import scala.reflect.BeanProperty
import java.util.Collection
import collection.JavaConversions._
import java.sql.Timestamp
import lards.global.Now


class N_to_m_join(
  @BeanProperty val table_name: String = "",
  @BeanProperty val id_a: java.lang.Long = null,
  @BeanProperty val timestamp_a: java.sql.Timestamp = null,
  @BeanProperty val id_b: java.lang.Long = null,
  @BeanProperty val timestamp_b: java.sql.Timestamp = null) {

  def this(table_name: String, user: User, location: Location) = this(table_name, user.id, user.timestamp, location.id, location.timestamp)
}
