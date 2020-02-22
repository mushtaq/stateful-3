package mushtaq

import scala.concurrent.duration.DurationDouble
import scala.concurrent.{ExecutionContext, Future}

class RbiStub(timerService: TimerService)(implicit ec: ExecutionContext) {

  def notification(): Future[Boolean] = {
    timerService.timeout(1.second).map(_ => true)
  }

}
