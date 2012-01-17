/**
 *
 */
package com.chatlogs.msn

import com.chatlogs.core.Message
import com.chatlogs.core.MessageTimestamp
import com.chatlogs.core.MessageType

/**
 * @author fqtrnt [2012/01/17]
 * @version 1.0.0
 *
 */
class MSNMessage(override val datetime: MessageTimestamp, override val from: String,
                 val to: String, val style: String, override val text: String, val sessionId: Int,
                 override val messageType: MessageType = MessageType.CHAT) extends Message {
  override val color: String = {
    val colorPattern = """.*color:#([0-9A-Fa-f]*).*""".r
    style match {
      case colorPattern(color) => color
      case _ => ""
    }
  }

  override def toString = "SessionId: " + sessionId + " DateTime: " + datetime + " From: " + from + " To: " + to + " Style: " + style + " Text: " + text
}