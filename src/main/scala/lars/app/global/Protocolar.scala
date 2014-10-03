/**
protocols are output from business-processes.
it only uses level 'info'.
the pattern-layout is expected to only print nothing but the message.
*/

package lars.global

import org.slf4j.LoggerFactory

trait Protocolar {
  var protocolar_logger: org.slf4j.Logger = null

  def create_protocolar(protocolar_name: String = "default") {
    protocolar_logger = LoggerFactory.getLogger(protocolar_name)
  }
  
  def protocol(msg: => String) = if (protocolar_logger.isDebugEnabled) protocolar_logger.info(msg)
}