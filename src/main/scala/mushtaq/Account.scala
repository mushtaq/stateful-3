package mushtaq

import java.util.concurrent.Executors

import mushtaq.Transaction.{Deposit, Withdrawal}

import scala.concurrent.{ExecutionContext, Future}

class Account(rbi: Rbi) {

  private var balance                 = 0
  var transactions: List[Transaction] = Nil

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))

  def deposit(amount: Int): Future[Unit] = Future {
    rbi.notification()
    balance += amount
    transactions ::= Deposit(amount)
  }

  def withdraw(amount: Int): Future[Unit] = Future {
    rbi.notification()
    balance -= amount
    transactions ::= Withdrawal(amount)
  }

  def getBalance: Future[Int] = Future {
    balance
  }

}
