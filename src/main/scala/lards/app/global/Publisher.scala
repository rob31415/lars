/**
this traint implements the ability to publish events to all it's subscribers
*/

package lards.global


trait Publisher[Evt] {
  // this is called "structural type"
  // @TODO: shouldn't that Any here rather be Evt?
  type Subscriber = { def notify(event: Any /*, publisher: Any*/) }

  var subscribers = List[Subscriber]()

  def subscribe(subscriber: Subscriber) = subscribers ::= subscriber
  def publish(event: Evt) = subscribers foreach (_.notify(event /*,this*/))
}
