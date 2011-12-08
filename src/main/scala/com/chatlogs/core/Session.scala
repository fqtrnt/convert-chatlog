package com.chatlogs.core

/**
 * Session is a set of message that between chat window opened and closed.<br>
 * User: fqtrnt [2011/11/23]
 * Version: 1.0.0
 */

class Session(sessionId: Int) {
  var messages: List[Message] = Nil

  def interlocutor: Interlocutor = messages.foldLeft(new Interlocutor) {
    (interlocutor, message) => {
      if (MessageType.CHAT == message.messageType && null != message.from) {
        interlocutor.spokesmen += message.from
      }
      interlocutor
    }
  }

  /**
   * Add the chat message to session, and return session itself.
   */
  def +[A <: Message](message: A): this.type = {
    messages ++= List(message)
    this
  }

  override def toString = String.valueOf(sessionId)
}

import scala.collection.mutable.Set

/**
 * Interlocutors of session at a time.
 */
class Interlocutor {
  var spokesmen = Set[String]()

  /**
   * For specified user name, recipients of message be found and returned.
   */
  def listeners(spokesman: String): Set[String] = {
    spokesmen filter (_ != spokesman)
  }
}