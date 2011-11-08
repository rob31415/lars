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
import lards.model.dto.{Aspect => Dto_aspect}
import lards.model.dto.Dto
import lards.model.dto.Dtos
import java.sql.Timestamp
import lards.global.Now
import scala.List
import lards.global.Logger



class Aspect(model_tags: Tag) extends Dao with Logger {

  val aspects = new scala.collection.mutable.HashSet[Dto_aspect]

  aspects += create_aspect(1, "Versichertenanteil Teilbezahlt", List('x1), List('x0))
  aspects += create_aspect(2, "Versichertenanteil Nachberechnung", List('m1), List('m0))
  aspects += create_aspect(3, "Transport berechenbar", List('rg), List('ru, 're))


  def create_aspect(id: Long, description: String, tag_symbols_imperative: List[Symbol], tag_symbols_prohibitive: List[Symbol]): Dto_aspect = {
    val aspect = new Dto_aspect(id, description)
    tag_symbols_imperative.foreach((mnemonic) => aspect.tag_imperative.add( model_tags.get(mnemonic).get ))
    tag_symbols_prohibitive.foreach((mnemonic) => aspect.tag_prohibitive.add( model_tags.get(mnemonic).get ))
    aspect
  }


  // arguments are irrelevant
  def _get_all(session: SqlSession, timestamp: Timestamp): Dtos = {
    val data = aspects.asInstanceOf[java.util.ArrayList[Dto]]
    return new Dtos(Some( new HashSet[Dto](data) ))
  }


  // arguments session and timestamp are irrelevant
  def _get(session: SqlSession, id: Long, timestamp: Timestamp): Option[Dto_aspect] = {
    val dto = aspects.find({e => e.id == id})
    log_debug("_get(): " + dto)
    return dto
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

}
