package lards.model.service

import org.apache.ibatis.session.SqlSession
import collection.JavaConversions._
import java.util._
import lards.global.Applocal
import lards.model.dto.{_REPLACE_ => Dto__REPLACE_}
import lards.model.dto.Dto
import lards.model.dto.Dtos
import lards.model.event.{_REPLACE_ => Event}



class _REPLACE_ extends Dao {

  def _get_all(session: SqlSession): Dtos = {
    val data = session.selectList("lars.model.mybatis.mapper._REPLACE_.get_all").asInstanceOf[java.util.ArrayList[Dto]]
    return new Dtos(Some( new HashSet[Dto](data) ))
  }


  def _get(session: SqlSession, id: Long): Option[Dto__REPLACE_] = {
    return Some( session.selectOne("lars.model.mybatis.mapper._REPLACE_.get_by_id", id).asInstanceOf[Dto__REPLACE_] )
  }


  def _save_new(session: SqlSession, record: Dto) {
    session.insert("lars.model.mybatis.mapper._REPLACE_.insert", record)
  }


  def _save_existing(session: SqlSession, record: Dto) {
    session.update("lars.model.mybatis.mapper._REPLACE_.update", record)
  }


  def _delete(session: SqlSession, record: Dtos) {
    session.delete("lars.model.mybatis.mapper._REPLACE_.delete", record.asJava)
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
