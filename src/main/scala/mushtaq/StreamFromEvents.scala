package mushtaq

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.Source

object StreamFromEvents {

  def main(args: Array[String]): Unit = {
    implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "demo")

    val (queue, stream) = Source.queue[Int](1024, OverflowStrategy.dropHead).preMaterialize()

    stream.runForeach(println)

    queue.offer(87)
    queue.offer(12)
    queue.offer(34)


  }

}
