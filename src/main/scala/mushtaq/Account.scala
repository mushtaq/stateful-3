package mushtaq

import mushtaq.Transaction.{Deposit, Withdrawal}

class Account {

  private var balance                 = 0
  var transactions: List[Transaction] = Nil

  def deposit(amount: Int): Unit = synchronized {
    rbi.notify()
    balance += amount
    transactions ::= Deposit(amount)
  }

  def withdraw(amount: Int): Unit = synchronized {
    rbi.notify()
    balance -= amount
    transactions ::= Withdrawal(amount)
  }

  def getBalance: Int = synchronized {
    balance
  }

}
