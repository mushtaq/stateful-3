package mushtaq

sealed trait Transaction
object Transaction {
  case class Deposit(amount: Int)    extends Transaction
  case class Withdrawal(amount: Int) extends Transaction
}
