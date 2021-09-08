package it.unibo.authsim.library.dsl
import it.unibo.authsim.library.dsl.Logger.LoggerImpl
import org.scalatest.funsuite.AnyFunSuite
import java.util.Calendar

import java.util.Date

object LoggerTest extends App{
    val logger= new LoggerImpl

    logger.receiveCracked(true)
    logger.receiveStatistics(Map.empty)
    logger.receiveExecutionTime(20)
  }

