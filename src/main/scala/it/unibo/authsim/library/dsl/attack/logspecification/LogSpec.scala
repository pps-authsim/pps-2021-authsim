package it.unibo.authsim.library.dsl.attack.logspecification

import it.unibo.authsim.library.dsl.stub.Logger

enum LogCategory:
  case ALL, SUCCESS, TIME, ATTEMPTS

implicit class LogSpec(private val logCategory: LogCategory) {
  private var categories = Set(logCategory)
  private var targetLogger: Option[Logger] = Option.empty

  def and(anotherCategory: LogCategory): LogSpec = {
    this.categories = this.categories + anotherCategory
    this
  }

  def to(logger: Logger): LogSpec = {
    this.targetLogger = Option(logger)
    this
  }

  def getCategories(): Set[LogCategory] = categories
  def getTargetLogger(): Option[Logger] = targetLogger
}
