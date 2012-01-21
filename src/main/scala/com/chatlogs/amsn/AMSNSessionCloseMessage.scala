package com.chatlogs.amsn

import com.chatlogs.core.{MessageType, Message, MessageTimestamp}

/**
 *
 * User: fqtrnt [2011/12/01]
 * Version: 1.0.0
 */

class AMSNSessionCloseMessage(override val datetime: MessageTimestamp, override val from: String = "",  override val text: String = "") extends Message {
  override val color: Option[String] = None
  override val messageType = MessageType.SYSTEM
  override def toString = "Session close at Datetime: " + datetime + " from: " + from + " text: " + text
}

object AMSNSessionCloseMessage {
  val sessionClose = """\|\"LRED\[You have closed the window on (.*)\]""".r
  val conferenceClose = """\|\"LRED\[(.*) has closed the window on (.*)\]""".r
  def unapply(messageLine: String): Option[(MessageTimestamp, String)] = {
    messageLine match {
      case sessionClose(time) => Some(time, "")
      case conferenceClose(from, time) => Some(time, from)
      case _ => None
    }
  }
}