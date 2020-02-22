package mushtaq

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import mushtaq.Transaction.{Deposit, Withdrawal}
import akka.actor.typed.scaladsl.AskPattern._
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration.DurationDouble

object ActorDemo {}

object AccountActor {
  sealed trait Operation
  case class DepositOp(amount: Int)                 extends Operation
  case class WithdrawalOp(amount: Int)              extends Operation
  case class GetBalanceOp(requester: ActorRef[Int]) extends Operation

  val behavior: Behavior[Operation] = Behaviors.setup { ctx =>
    ctx.self.tell()
    var balance                         = 0
    var transactions: List[Transaction] = Nil

    Behaviors.receiveMessage {
      case DepositOp(amount) =>
        balance += amount
        transactions ::= Deposit(amount)
        Behaviors.same
      case WithdrawalOp(amount) =>
        balance -= amount
        transactions ::= Withdrawal(amount)
        Behaviors.same
      case GetBalanceOp(requester) =>
        requester.tell(balance)
        Behaviors.same
    }
  }

  def main(args: Array[String]): Unit = {
    implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "demo")
    import actorSystem.executionContext

    val actorRef: ActorRef[Operation] = actorSystem.systemActorOf(behavior, "account-1")

    implicit val timeout: Timeout = Timeout(5.seconds)

    actorRef.tell(DepositOp(100))
    val future: Future[Int] = actorRef.ask(requester => GetBalanceOp(requester))

    future.onComplete(println)
  }

}
