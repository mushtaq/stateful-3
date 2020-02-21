package mushtaq

import scala.concurrent.{ExecutionContext, Future}
import async.Async._

class ProductService(implicit ec: ExecutionContext) {

  val m = Map("id1" -> 100, "id2" -> 200)

  // return if after 100 ms, this is an external service call
  def getPrice(productId: String): Future[Int] = Future {
    m(productId)
  }

  def getTotalPrice(id1: String, id2: String): Future[Int] = {
    val f1 = getPrice(id1)
    val f2 = getPrice(id2)
    f1.flatMap { price1 => f2.map { price2 => price1 + price2 } }
  }

  def getTotalPrice2(id1: String, id2: String): Future[Int] = async {
    val f1     = getPrice(id1)
    val f2     = getPrice(id2)
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
