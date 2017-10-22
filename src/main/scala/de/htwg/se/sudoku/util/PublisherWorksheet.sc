import scala.swing.event.Event
import scala.swing.{Publisher, Reactor}

println("Hello")

1+2

class Pub(a:Int) extends Publisher {
  val b=a
}

class Ev extends Event

class Sub(publisher: Publisher) extends Reactor {
  listenTo(publisher)
  reactions += {
    case event: Ev => println("got event")
  }
}

val p = new Pub(7)