/**
the dtos of this dao are not user-modifiable data but 
represent correlations of the system-internal workings.

the dtos of this dao are hardcoded and read-only.

there is no database storage for dtos of this dao.

this is because we want to use system-internal data
in the same means as user-modified data.
*/

package lards.model.service

import org.apache.ibatis.session.SqlSession
import collection.JavaConversions._
import java.util._
import lards.global.Applocal
import lards.model.dto.{Tag => Dto_tag}
import lards.model.dto.Dto
import lards.model.dto.Dtos
import java.sql.Timestamp
import lards.global.Now



class Tag extends Dao {

  val tags = new scala.collection.mutable.HashSet[Dto_tag]

  tags += new Dto_tag(1, 'ru, "rechnung unverarbeitet")
  tags += new Dto_tag(2, 'rg, "rechnung ist geprüft")
  tags += new Dto_tag(3, 're, "rechnung ist erstellt")
  tags += new Dto_tag(4, 'x0, "kein x fall")
  tags += new Dto_tag(5, 'x1, "x fall")
  tags += new Dto_tag(6, 'm0, "kein m fall")
  tags += new Dto_tag(7, 'm1, "m fall")



  // arguments are irrelevant
  def _get_all(session: SqlSession, timestamp: Timestamp): Dtos = {
    val data = tags.asInstanceOf[java.util.ArrayList[Dto]]
    return new Dtos(Some( new HashSet[Dto](data) ))
  }


  // arguments session and timestamp are irrelevant
  def _get(session: SqlSession, id: Long, timestamp: Timestamp): Option[Dto_tag] = {
    return tags.find({e => e.id == id})
  }


  // not implemented
  def _get_all_history(session: SqlSession, timestamp: Timestamp, filter_begin: Dto, filter_end: Dto): Dtos = {
    return new Dtos(None)
  }


  // not implemented
  def _get_history(session: SqlSession, id: Long, timestamp: Timestamp): Dtos = {
    return new Dtos(None)
  }


  def _save(session: SqlSession, record: Dto) {
    // nop
  }


  def _overwrite(session: SqlSession, record: Dto) {
    // nop
  }


  def _delete(session: SqlSession, record: Dtos) {
    // nop
  }


  def on_success_delete() {
    // nop
  }


  def on_success_save() {
    // nop
  }


  def on_success_overwrite() {
    // nop
  }


  def get(mnemonic: Symbol): Option[Dto_tag] = {
    println("Tag get_by_mnemonic " + mnemonic)
    tags.find(el => el.mnemonic == mnemonic)
  }

}
