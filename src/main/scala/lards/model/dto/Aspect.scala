/**
*/

package lards.model.dto


object Aspect {
  val get_all = new scala.collection.mutable.HashSet[Aspect]
  get_all += new Aspect("x", "Versichertenanteil Teilbezahlt")
  get_all += new Aspect("m", "Versichertenanteil Nachberechnung")
}


class Aspect(val mnemonic: String, 
  val description: String) {

  @BeanProperty var tag = new java.util.HashSet[Tag]
}

