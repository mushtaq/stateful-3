package mushtaq

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

import scala.concurrent.{Future, Promise}

class Rbi {

  private val service: ScheduledExecutorService = Executors.newScheduledThreadPool(1)

  def notification(): Future[Boolean] = {
    val p: Promise[Boolean] = Promise()
    service.schedule(() => p.trySuccess(true), 100, TimeUnit.MILLISECONDS)
    p.future
  }

}
