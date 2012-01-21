package com.chatlogs.amsn

import com.chatlogs.core.{MessageType, MessageTimestamp, Message}

/**
 *
 * User: fqtrnt [2011/12/01]
 * Version: 1.0.0
 */

class AMSNMessage(override val datetime: MessageTimestamp, override val from: String, override val color: Option[String], override val text: String) extends Message {
  override val messageType = MessageType.CHAT
  override def toString = "Datetime: " + datetime + " from: " + from + " color: " + color.getOrElse("") + " text: " + text
}

object AMSNMessage {
  val chat = """\|\"LGRA\[\|\"LTIME(\d*) \] \|\"LITA(.*) :\|\"LC(\w*) (.*)""".r
  val transferFile = """\|\"LGRA\|\"LTIME(\d*) \|\"LGRE(.*)""".r
  def unapply(messageLine: String): Option[(MessageTimestamp, String, Option[String], String)] = {
    messageLine match {
      case chat(time, from, color, content) => Some(time, from, Some(color), content)
      case transferFile(time, content) => Some(time, null, None, content)
      case _ => None
    }
  }
}