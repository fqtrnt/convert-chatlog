package com.chatlogs.core

/**
 *
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

  def +[A <: Message](message: A): this.type = {
    messages ++= List(message)
    this
  }

  override def toString = String.valueOf(sessionId)
}

import scala.collection.mutable.Set
class Interlocutor {
  var spokesmen = Set[String]()

  def listeners(spokesman: String): Set[String] = {
    spokesmen filter (_ != spokesman)
  }
}