/**
logs are for the technical aspect to help tracing the program flow on the basis of the sourcecode.
*/

package lars.global

import org.slf4j.LoggerFactory

trait Logger {
  val logger = LoggerFactory.getLogger(getClass)
  def log_debug(msg: => String) = if (logger.isDebugEnabled) logger.debug(msg)
  def log_warn(msg: => String) = if (logger.isWarnEnabled) logger.warn(msg)
  def log_error(msg: => String, e:Throwable) = if (logger.isErrorEnabled) logger.error(msg,e)
}