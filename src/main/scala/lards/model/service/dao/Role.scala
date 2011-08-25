package lards.model.service

import org.apache.ibatis.session.SqlSession
import collection.JavaConversions._
import java.util._
import lards.global.Applocal
import lards.model.dto.{Role => Dto_role}
import lards.model.dto.Dto
import lards.model.dto.Dtos
import lards.model.event.{Role => Event}



class Role extends Dao {

  def _get_all(session: SqlSession): Dtos = {
    val data = session.selectList("lars.model.mybatis.mapper.role.get_all").asInstanceOf[java.util.ArrayList[Dto]]
    return new Dtos(Some( new HashSet[Dto](data) ))
  }


  def _get(session: SqlSession, id: Long): Option[Dto_role] = {
    return Some( session.selectOne("lars.model.mybatis.mapper.role.get_by_id", id).asInstanceOf[Dto_role] )
  }


  def _save_new(session: SqlSession, record: Dto) {
    session.insert("lars.model.mybatis.mapper.role.insert", record)
  }


  def _save_existing(session: SqlSession, record: Dto) {
    session.update("lars.model.mybatis.mapper.role.update", record)
  }


  def _delete(session: SqlSession, record: Dtos) {
    //example: delete from role where description = 'blub' or description = 'bla!';
    session.delete("lars.model.mybatis.mapper.role.delete", record.asJava)
  }


  def on_success_delete() {
    Applocal.broadcaster.publish(new Event('deleted))
  }


  def on_success_insert() {
    Applocal.broadcaster.publish(new Event('inserted))
  }


  def on_success_update() {
    Applocal.broadcaster.publish(new Event('updated))
  }

}
