package lards.global


trait Publisher[Evt] {
  type Subscriber = { def notify(event: Any /*, publisher: Any*/) }

  var subscribers = List[Subscriber]()

  def subscribe(subscriber: Subscriber) = subscribers ::= subscriber
  def publish(event: Evt) = subscribers foreach (_.notify(event /*,this*/))
}
