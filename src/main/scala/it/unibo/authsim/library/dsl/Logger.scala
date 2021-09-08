package it.unibo.authsim.library.dsl
import java.util.Date

trait Logger 
  def receiveExecutionTime(executionTime: Long): Unit
  def receiveStatistics(map: Map [String, String]): Unit
  def receiveCracked(flag: Boolean): Unit

object Logger
  def apply(): Logger = LoggerImpl()
  case class LoggerImpl() extends Logger 
    override def receiveExecutionTime(executionTime:  Long): Unit= println(s"Execution time: $executionTime")
    override def receiveStatistics(statistics: Map [String, String]): Unit = println(s"Statistics:${statistics.toString()}")
    override def receiveCracked(flag: Boolean): Unit = println(if(flag) then "The attak succeded: password cracked" else "The attack did not succeded")