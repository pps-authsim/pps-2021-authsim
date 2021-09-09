package it.unibo.authsim.library.dsl
import it.unibo.authsim.library.dsl.Logger.*
import org.scalatest.funsuite.AnyFunSuite
import java.util.Calendar

object LoggerTest extends App{
    val logger= Logger

    logger.receiveCracked(true)
    logger.receiveStatistics(Map.empty)
    logger.receiveExecutionTime(20)
  }

