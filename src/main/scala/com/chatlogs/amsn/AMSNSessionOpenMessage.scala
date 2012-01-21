package com.chatlogs.amsn

import com.chatlogs.core.{MessageType, Message, MessageTimestamp}

/**
 *
 * User: fqtrnt [2011/12/01]
 * Version: 1.0.0
 */

class AMSNSessionOpenMessage(override val datetime: MessageTimestamp, override val from: String = "", override val text: String = "") extends Message {
  override val color: Option[String] = None
  override val messageType = MessageType.SYSTEM
  override def toString = "Session open at Datetime: " + datetime + " from: " + from + " text: " + text
}

object AMSNSessionOpenMessage {
  val sessionOpen = """\|\"LRED\[Conversation started on \|\"LTIME(\d*)\]""".r
  val conferenceOpen = """\|\"LRED\[(.*) has entered into a conference on \|\"LTIME(\d*)(.*)\]""".r
  def unapply(messageLine: String): Option[(MessageTimestamp, String, String)] = {
    messageLine match {
      case sessionOpen(time) => Some(time, "", "")
      case conferenceOpen(from, time, content) => Some(time, from, content)
      case _ => None
    }
  }
}