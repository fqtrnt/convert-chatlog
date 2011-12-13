package com.chatlogs.amsn

import com.chatlogs.core.{MessageTimestamp, MessageType, Message}

/**
 *
 * User: fqtrnt [2011/12/01]
 * Version: 1.0.0
 */

class AMSNEmptyMessage extends Message {
  override val datetime = new MessageTimestamp(null)
  override val from = ""
  override val color = ""
  override val text = "\n"
  override val messageType = MessageType.CHAT
  override def toString = text
}