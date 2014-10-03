/*
a implementation of a dao must define all the methods
dictated by this class.

the method-names correspond with the 
names in Dao.scala (prefixed with "_").
for documentation please see Dao.scala.
*/

package lars.model.service

import org.apache.ibatis.session.SqlSession
import lars.model.dto.Dto
import lars.model.dto.Dtos
import java.sql.Timestamp



abstract class Dao_overrideable {

  def _get_all(session: SqlSession, timestamp: Timestamp): Dtos

  def _get(session: SqlSession, id: Long, timestamp: Timestamp): Option[Dto]

  def _get_all_history(session: SqlSession, timestamp: Timestamp, filter_begin: Dto, filter_end: Dto): Dtos

  def _get_history(session: SqlSession, id: Long, timestamp: Timestamp): Dtos

  def _save(session: SqlSession, record: Dto)
  def on_success_save()

  def _overwrite(session: SqlSession, record: Dto)
  def on_success_overwrite()

  def _delete(session: SqlSession, record: Dtos)
  def on_success_delete()
  
}
