package lars.model.dto

import collection.JavaConversions._
import lars.model.dto.Dto

// voodoo from http://stackoverflow.com/questions/2161830/scala-2-8-implict-java-collections-conversions
// PS: voodoo is a bad thing (because one don't understand how it' supposed to work)
class A[K](m : collection.Set[K]) {
 def asJava(implicit ma:ClassManifest[K]) : java.util.Set[K] = m
}


class Dtos(var get: Option[scala.collection.Set[Dto]] = None) {
  def asJava = {
    new A(get.get).asJava
  }
}

//"illegal inheritance from sealed class Option". that's gay!
//class Dtos extends Option[Seq[Dto]]
