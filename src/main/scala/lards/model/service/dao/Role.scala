package lards.model.service

import lards.model.dto.{Role => _Role}
import collection.JavaConversions._
import org.apache.ibatis.session.SqlSession


class Role extends Dao[_Role] {

  def _get_all(session: SqlSession): Recordlist = {
    return Some( session.selectList("lards.model.dto.RoleMapper.getRoles").asInstanceOf[java.util.List[_Role]] )
  }

  def _get(session: SqlSession, id: Long): Option[_Role] = {
    return Some( session.selectOne("lards.model.dto.RoleMapper.getRole", id).asInstanceOf[_Role] )
  }

  def _save_new(session: SqlSession, record: _Role) {
    session.insert("lards.model.dto.RoleMapper.insertRole", record)
    //Broadcaster.publish(new lards.model.event.Change)
  }

  def _save_existing(session: SqlSession, record: _Role) {
    session.update("lards.model.dto.RoleMapper.updateRole", record)
    //Broadcaster.publish(new lards.model.event.Change)
  }

  def _delete(session: SqlSession, record: _Role) {
    session.delete("lards.model.dto.RoleMapper.deleteRole", record.id)
    //Broadcaster.publish(new lards.model.event.Change)
  }

}
