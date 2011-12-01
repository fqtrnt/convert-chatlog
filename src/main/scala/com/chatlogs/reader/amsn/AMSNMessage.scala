package com.chatlogs.reader.amsn

import com.chatlogs.core.{MessageType, MessageTimestamp, Message}

/**
 *
 * User: fqtrnt [2011/12/01]
 * Version: 1.0.0
 */

class AMSNMessage(override val datetime: MessageTimestamp, override val from: String, override val color: String, override val text: String) extends Message {
  override val messageType = MessageType.CHAT
  override def toString = "Datetime: " + datetime + " from: " + from + " color: " + color + " text: " + text
}