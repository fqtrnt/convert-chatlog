package com.chatlogs.reader.amsn

import com.chatlogs.core.{MessageType, MessageTimestamp, Message}

/**
 *
 * User: fqtrnt [2011/12/01]
 * Version: 1.0.0
 */

class AMSNWrapMessage(val content: String) extends Message {
  override val datetime: MessageTimestamp = new MessageTimestamp(null)
  override val from: String = "";
  override val color: String = "";
  override val text: String = "\n" + content
  override val messageType = MessageType.CHAT
  override def toString = text
}