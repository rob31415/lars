/*
package lards.model.service

import lards.model.dto.{User => Dto_user}
//import lards.global.Broadcaster
//import lards.model.event._
import collection.JavaConversions._
//import java.util.logging.Logger
//import org.apache.ibatis.session._
//import org.apache.ibatis.io._
import org.apache.ibatis.session.SqlSession


class User extends Dao[Dto_user] {

  def _get_all(session: SqlSession): Recordlist = {
    return Some( session.selectList("lards.model.dto.UserMapper.getUsers").asInstanceOf[java.util.List[Dto_user]] )
  }

  def _get(session: SqlSession, id: Long): Option[Dto_user] = {
    return Some( session.selectOne("lards.model.dto.UserMapper.getUser", id).asInstanceOf[Dto_user] )
  }

  def _save_new(session: SqlSession, record: Dto_user) {
    session.insert("lards.model.dto.UserMapper.insertUser", record)
    //Broadcaster.publish(new lards.model.event.Change)
  }

  def _save_existing(session: SqlSession, record: Dto_user) {
    session.update("lards.model.dto.UserMapper.updateUser", record)
    //Broadcaster.publish(new lards.model.event.Change)
  }

  def _delete(session: SqlSession, record: java.util.Set[Dto_user]) {
    //session.delete("lards.model.dto.UserMapper.deleteUser", record.id)
    //Broadcaster.publish(new lards.model.event.Change)
  }

  def get_roles(user: Dto_user) : Dto_user = {
    return user
  }

  def get_locations(user: Dto_user) : Dto_user = {
    return user
  }

  def on_success_delete() {
  }

  def on_success_insert() {
  }

  def on_success_update() {
  }

}
*/
