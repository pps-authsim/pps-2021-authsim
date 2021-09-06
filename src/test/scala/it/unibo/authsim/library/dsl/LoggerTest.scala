package it.unibo.authsim.library.dsl
import it.unibo.authsim.library.dsl.Logger.LoggerImpl
import org.scalatest.funsuite.AnyFunSuite

import java.util.Date

class LoggerTest extends AnyFunSuite {

  test("An logger should print readable string") {
    var logger = new LoggerImpl
    var date = new Date
    var map = Map.empty("prova","prova")

    logger.receivelsCracked(true)
    logger.receiveStatistics(map)
    logger.receiveExecutionTime(date.getTime)
  }
}
