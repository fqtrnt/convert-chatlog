package com.chatlogs.reader.amsn

import com.chatlogs.core.{MessageType, Message, MessageTimestamp}

/**
 *
 * User: fqtrnt [2011/12/01]
 * Version: 1.0.0
 */

class AMSNSessionOpenMessage(override val datetime: MessageTimestamp, override val from: String = "", override val text: String = "") extends Message {
  override val color: String = null
  override val messageType = MessageType.SYSTEM
  override def toString = "Session open at Datetime: " + datetime + " from: " + from + " text: " + text
}