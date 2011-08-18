/**
Holds Data with the notion of keeping it seperated
for every session which lives longer than a servlet-thread
(which is in fact a application).

@TODO:
-user
-i18n (locale & resources)
*/

package lards.global

import com.vaadin.Application 
import com.vaadin.service.ApplicationContext.TransactionListener
import lards.global.Publisher
import lards.global.Event



class Applocal extends TransactionListener {

  class Broadcaster extends Publisher[Event]

  val broadcaster = new Broadcaster()

  var user: lards.model.dto.User = null


  override def transactionStart(app: Application, transactionData: Object) {
      lards.global.Applocal.threadLocal.set(this)
  }


  override def transactionEnd(app: Application, transactionData: Object) {
      lards.global.Applocal.threadLocal.set(null)
  }


  def set_user(user: lards.model.dto.User) {
    this.user = user
  }
  

  def get_user = this.user

}


object Applocal {
  val threadLocal = new ThreadLocal[Applocal]()

  def init(app: Application) {
    threadLocal.set(new Applocal())
    app.getContext().addTransactionListener(threadLocal.get())
  }

  def broadcaster: lards.global.Applocal#Broadcaster = threadLocal.get.broadcaster

  def get_user = threadLocal.get.get_user

  def set_user(user: lards.model.dto.User) = threadLocal.get.set_user(user)
  
}

