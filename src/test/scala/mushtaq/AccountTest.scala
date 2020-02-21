package mushtaq

import java.util.concurrent.Executors

import org.scalatest.funsuite.AnyFunSuite

import scala.concurrent.duration.DurationDouble
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutorService, Future}

class AccountTest extends AnyFunSuite {

  private val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(10000))

  test("demo") {
    val account = new Account

    Future {
      (1 to 10000).foreach { _ =>
        Future {
          account.deposit(100)
        }(ec)
      }
    }(ec)

    Future {
      (1 to 10000).foreach { _ =>
        Future {
          account.withdraw(100)
        }(ec)
      }
    }(ec)

//    Thread.sleep(1000)

    val eventualInt = Future(account.getBalance)(ec)

    val value = Await.result(eventualInt, 5.seconds)
    println(value)


  }

}
