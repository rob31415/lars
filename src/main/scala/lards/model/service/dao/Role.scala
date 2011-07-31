package lards.model.service

import lards.model.dto.{Role => _Role}
import collection.JavaConversions._
import org.apache.ibatis.session.SqlSession
import lards.global.Applocal


class Role extends Dao[_Role] {

  def _get_all(session: SqlSession): Recordlist = {
    return Some( session.selectList("lars.model.mybatis.mapper.role.get_all").asInstanceOf[java.util.List[_Role]] )
  }

  def _get(session: SqlSession, id: Long): Option[_Role] = {
    return Some( session.selectOne("lars.model.mybatis.mapper.role.get_by_id", id).asInstanceOf[_Role] )
  }

  def _save_new(session: SqlSession, record: _Role) {
    session.insert("lars.model.mybatis.mapper.role.insert", record)
    //Broadcaster.publish(new lards.model.event.Change)
  }

  def _save_existing(session: SqlSession, record: _Role) {
    session.update("lars.model.mybatis.mapper.role.update", record)
    //Broadcaster.publish(new lards.model.event.Change)
  }

  def _delete(session: SqlSession, record: _Role) {
    session.delete("lars.model.mybatis.mapper.role.delete", record.id)
    //Broadcaster.publish(new lards.model.event.Change)
  }

  def save(record: _Role) = {
    if(record.id == -1)
      save_new(record)
    else
      save_existing(record)

    Applocal.broadcaster.publish(new lards.model.event.Change('role))
  }

}
