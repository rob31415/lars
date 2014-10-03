/**
Holds Data with the notion of keeping it seperated
for every session which lives longer than a servlet-thread
(which is in fact a application).

@TODO:
-user
-i18n (locale & resources)
*/

package lars.global

import com.vaadin.Application 
import com.vaadin.service.ApplicationContext.TransactionListener
import lars.global.Publisher
import lars.global.Event



class Applocal extends TransactionListener {

  class Broadcaster extends Publisher[Event]

  val broadcaster = new Broadcaster()

  var user: lars.model.dto.User = null


  override def transactionStart(app: Application, transactionData: Object) {
      lars.global.Applocal.threadLocal.set(this)
  }


  override def transactionEnd(app: Application, transactionData: Object) {
      lars.global.Applocal.threadLocal.set(null)
  }


  def set_user(user: lars.model.dto.User) {
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

  def broadcaster: lars.global.Applocal#Broadcaster = threadLocal.get.broadcaster

  def get_user = threadLocal.get.get_user

  def set_user(user: lars.model.dto.User) = threadLocal.get.set_user(user)
  
}

