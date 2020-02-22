package mushtaq

import java.util.concurrent.Executors

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.scaladsl.Source

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationDouble

object StreamDemo {

  def main(args: Array[String]): Unit = {

    implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "demo")
    implicit val ec: ExecutionContext              = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))

    val productService = new ProductService(new TimerService())

    val prices = Source(1 to 100).map(x => s"id-$x").mapAsyncUnordered(25) { id => productService.getPrice(id) }

//    prices.runForeach(println)

//    Source.tick(0.millis, 100.millis, ()).runForeach(println)
    Source
      .fromIterator(() => Iterator.from(1))
      .throttle(1, 1.second)
      .runForeach(println)
  }

}
