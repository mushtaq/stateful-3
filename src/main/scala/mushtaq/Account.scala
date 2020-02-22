package mushtaq

import java.util.concurrent.Executors

import akka.NotUsed
import akka.actor.typed.ActorSystem
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.Source
import mushtaq.Transaction.{Deposit, Withdrawal}

import scala.concurrent.{ExecutionContext, Future}

class Account(rbi: RbiStub)(implicit actorSystem: ActorSystem[_]) {

  private var balance                 = 0
  var transactions: List[Transaction] = Nil

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))
  private val (queue, stream)       = Source.queue[Transaction](1024, OverflowStrategy.dropHead).preMaterialize()

  def deposit(amount: Int): Future[Unit] = rbi.notification().map { bool =>
    if (bool) {
      balance += amount
      transactions ::= Deposit(amount)
      queue.offer(Deposit(amount))
    }
  }

  def withdraw(amount: Int): Future[Unit] = rbi.notification().map { _ =>
    balance -= amount
    transactions ::= Withdrawal(amount)
    queue.offer(Withdrawal(amount))
  }

  def getBalance: Future[Int] = Future {
    balance
  }

  def getStream: Source[Transaction, NotUsed] = stream

}
