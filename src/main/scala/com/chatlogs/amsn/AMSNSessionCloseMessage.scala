package com.chatlogs.amsn

import com.chatlogs.core.{MessageType, Message, MessageTimestamp}

/**
 *
 * User: fqtrnt [2011/12/01]
 * Version: 1.0.0
 */

class AMSNSessionCloseMessage(override val datetime: MessageTimestamp, override val from: String = "", override val color: String = "", override val text: String = "") extends Message {
  override val messageType = MessageType.SYSTEM
  override def toString = "Session close at Datetime: " + datetime + " from: " + from + " text: " + text

}