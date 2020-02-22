package mushtaq

import scala.annotation.tailrec
import scala.async.Async._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object SequenceImpl {

  def sequence(futures: List[Future[Int]]): Future[List[Int]] = async {
    var result: List[Int] = Nil
    val iterator          = futures.iterator
    while (iterator.hasNext) {
      result = await(iterator.next()) :: result
    }
    result.reverse
  }

  def sequence2(futures: List[Future[Int]]): Future[List[Int]] = futures match {
    case Nil          => Future.successful(Nil)
    case head :: tail => sequence2(tail).flatMap(xs => head.map(x => x :: xs))
  }

  def sequence3(futures: List[Future[Int]]): Future[List[Int]] = {
    futures.foldLeft(Future.successful(List.empty[Int]))((xsF, xF) => xsF.flatMap(xs => xF.map(x => x :: xs))).map(_.reverse)
  }

  def main(args: Array[String]): Unit = {
    val futures: List[Future[Int]] = List(Future(10), Future(20))
    val result: Future[List[Int]]  = sequence3(futures)
    result.foreach(println)

    Thread.sleep(Int.MaxValue)
  }

}
