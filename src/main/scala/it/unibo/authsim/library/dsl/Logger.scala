package it.unibo.authsim.library.dsl
import java.util.Date

trait Logger {
  def receiveExecutionTime(executionTime: Long): Unit
  def receiveStatistics(map: Map [String, String]): Unit
  def receivelsCracked(flag: Boolean): Unit
}

object Logger{
  def apply(): Logger = LoggerImpl()
  case class LoggerImpl() extends Logger {
    override def receiveExecutionTime(executionTime:  Long): Unit= println(s"Execution time: $executionTime")
    override def receiveStatistics(statistics: Map [String, String]): Unit = println(s"Statistics:${statistics.toString()}")
    override def receivelsCracked(flag: Boolean): Unit = println(if(flag) then "The attak succeded: password cracked" else "The attack did not succeded")
  }
}

object LoggerTest extends App{
  import it.unibo.authsim.library.dsl.Logger.LoggerImpl
  val logger= new LoggerImpl
  val time = new Date()

  logger.receivelsCracked(true)
  logger.receiveStatistics(Map.empty)
  logger.receiveExecutionTime(time.getTime)
}