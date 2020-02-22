package mushtaq

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.util.control.NonFatal

object ErrorManagement {

  def sequence(futures: List[Future[Int]]): Future[List[Int]] = {
    //use async await
    // maybe a promise will be required
    futures.iterator
    ???
  }

  private val futures: List[Future[Int]] = List(Future.successful(10), Future.successful(20))
  private val result: Future[List[Int]]  = sequence(futures)

  def main(args: Array[String]): Unit = {

    val future: Future[Int] =
      Future.failed(new ArithmeticException("message from exception"))

    future.foreach { x => println(x) }

    future.recover {
      case NonFatal(ex) => println(ex.getMessage)
    }

    future.onComplete {
      case Success(value) =>
      case Failure(ex)    => println(ex.getMessage)
    }

    Thread.sleep(Int.MaxValue)
  }

}
