package mushtaq

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.control.NonFatal
import scala.util.{Failure, Success}

object ErrorManagement {

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
