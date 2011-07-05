package lards.model.service

import lards.model.dto.{User => _User}
//import lards.global.Broadcaster
//import lards.model.event._
import collection.JavaConversions._
//import java.util.logging.Logger
//import org.apache.ibatis.session._
//import org.apache.ibatis.io._
import org.apache.ibatis.session.SqlSession


class User extends Dao[_User] {

  def _get_all(session: SqlSession): Recordlist = {
    return Some( session.selectList("lards.model.dto.UserMapper.getUsers").asInstanceOf[java.util.List[_User]] )
  }

  def _get(session: SqlSession, id: Long): Option[_User] = {
    return Some( session.selectOne("lards.model.dto.UserMapper.getUser", id).asInstanceOf[_User] )
  }

  def _save_new(session: SqlSession, record: _User) {
    session.insert("lards.model.dto.UserMapper.insertUser", record)
    //Broadcaster.publish(new lards.model.event.Change)
  }

  def _save_existing(session: SqlSession, record: _User) {
    session.update("lards.model.dto.UserMapper.updateUser", record)
    //Broadcaster.publish(new lards.model.event.Change)
  }

  def _delete(session: SqlSession, record: _User) {
    session.delete("lards.model.dto.UserMapper.deleteUser", record.id)
    //Broadcaster.publish(new lards.model.event.Change)
  }

  def get_roles(user: _User) : _User = {
    return user
  }

  def get_locations(user: _User) : _User = {
    return user
  }

}
