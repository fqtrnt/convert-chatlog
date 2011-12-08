package com.chatlogs.core

/**
 * Division of message in message log file.<br>
 * There be contain the system messages and chat messages.
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