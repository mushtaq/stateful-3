package mushtaq

import java.util.concurrent.Executors

import akka.NotUsed
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.scaladsl.Source
import mushtaq.Transaction.{Deposit, Withdrawal}
import org.scalatest.funsuite.AnyFunSuite

import scala.concurrent.duration.DurationDouble
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutorService, Future}

class AccountTest extends AnyFunSuite {

  implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "demo")
  import actorSystem.executionContext

  private val timerService = new TimerService()
  test("demo") {
    val account = new Account(new RbiStub(timerService))
    val account2 = new Account(new RbiStub(timerService))

    Source
      .fromIterator(() => Iterator.from(1))
      .throttle(1, 1.second)
      .runForeach(x => account.deposit(x))

//    Source
//      .fromIterator(() => Iterator.from(1))
//      .throttle(1, 1.second)
//      .runForeach(x => account.withdraw(x))

//    val depositFutures: Seq[Future[Unit]] = (1 to 1000).map { _ => account.deposit(100) }

//    val withdrawalFutures: Seq[Future[Unit]] = (1 to 1000).map { _ => account.withdraw(100) }

    val mergedStream = account.getStream.merge(account2.getStream)
    account.getStream.runForeach(println)

    val runningBalance: Source[Int, NotUsed] = mergedStream.scan(0) {
      case (acc, Deposit(amount)) => acc + amount
      case (acc, Withdrawal(amount)) => acc - amount
    }

    runningBalance.runForeach(println)
    
    Thread.sleep(Int.MaxValue)

//    val allFutures: Seq[Future[Unit]] = depositFutures ++ withdrawalFutures

//    val future: Future[Seq[Unit]] = Future.sequence(allFutures)
//
//    val value = Await.result(future, 5.seconds)
//    println(value)

//    Thread.sleep(1000)
//    println(Await.result(account.getBalance, 5.seconds))

  }

  test("timeout") {
    val future  = timerService.timeout(100.millis)
    val future2 = timerService.completeWithin(future, 50.millis)
//    Await.result(future2, 5.seconds)
    Thread.sleep(5000)
  }

}
