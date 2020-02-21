package mushtaq

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, Future}

class Rbi {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))

  def notification(): Future[Unit] = Future {
    Thread.sleep(100)
  }

}
