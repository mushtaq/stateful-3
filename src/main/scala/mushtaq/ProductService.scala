package mushtaq

import scala.concurrent.{ExecutionContext, Future}
import async.Async._
import scala.concurrent.duration.DurationDouble
import scala.util.Random

class ProductService(timerService: TimerService)(implicit ec: ExecutionContext) {

  // return if after 100 ms, this is an external service call
  def getPrice(productId: String): Future[Int] = {
    println(s"calling price service for id=$productId")
    timerService.timeout(Random.nextInt(5).seconds).map(_ => Random.nextInt(100))
  }

  def getTotalPrice(id1: String, id2: String): Future[Int] = {
    val f1 = getPrice(id1)
    val f2 = getPrice(id2)
    f1.flatMap { price1 => f2.map { price2 => price1 + price2 } }
  }

  def getTotalPrice2(id1: String, id2: String): Future[Int] = async {
    val f1 = getPrice(id1)
    val f2 = getPrice(id2)
    await(f1) + await(f2)
  }

  def getTotalPrice(ids: List[String]): Future[Int] = {
    val futures: List[Future[Int]] = ids.map(id => getPrice(id))
    val future: Future[List[Int]]  = Future.sequence(futures)
    future.map(list => list.sum)
  }

  def getTotalPrice2(ids: List[String]): Future[Int] = {
    val future = Future.traverse(ids) { id => getPrice(id) }
    future.map(list => list.sum)
  }

}

object ProductService {
  def main(args: Array[String]): Unit = {
    import ExecutionContext.Implicits.global
    val service = new ProductService(new TimerService())

//    service.getPrice("id1").onComplete(println)
//    service.getTotalPrice("id1", "id2").onComplete(println)
//    service.getTotalPrice(List("id1", "id2")).onComplete(println)

    val ids = (1 to 100).toList.map(x => s"id-$x")
    service.getTotalPrice(ids).onComplete(println)

    Thread.sleep(Int.MaxValue)
  }
}
