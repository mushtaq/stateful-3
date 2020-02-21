package mushtaq

import java.util.concurrent.Executors

import org.scalatest.funsuite.AnyFunSuite

import scala.concurrent.duration.DurationDouble
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutorService, Future}

class AccountTest extends AnyFunSuite {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))

  test("demo") {
    val account = new Account(new Rbi)

    val depositFutures: Seq[Future[Unit]] = (1 to 1000).map { _ => account.deposit(100) }

    val withdrawalFutures: Seq[Future[Unit]] = (1 to 1000).map { _ => account.withdraw(100) }

    val allFutures: Seq[Future[Unit]] = depositFutures ++ withdrawalFutures

    val future: Future[Seq[Unit]] = Future.sequence(allFutures)

    Await.result(future, 5.seconds)

    val eventualInt = account.getBalance

    val value = Await.result(eventualInt, 5.seconds)
    println(value)

  }

}
