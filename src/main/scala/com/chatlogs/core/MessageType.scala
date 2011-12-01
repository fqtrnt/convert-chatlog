package com.chatlogs.core

/**
 *
 * User: fqtrnt [2011/11/28]
 * Version: 1.0.0
 */
sealed abstract class MessageType
case class System() extends MessageType
case class Chat() extends MessageType
object MessageType {
  val SYSTEM = System()
  val CHAT = Chat()
}