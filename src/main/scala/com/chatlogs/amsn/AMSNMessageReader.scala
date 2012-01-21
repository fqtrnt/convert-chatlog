package com.chatlogs.amsn

import com.chatlogs.core.util.IOUtils._
import io.Source
import com.chatlogs.core.{Session, Message, MessageReader}
import scala.io.Codec

/**
 *
 * User: fqtrnt [2011/12/01]
 * Version: 1.0.0
 */

class AMSNMessageReader extends MessageReader {
  def read(folder: String, target: String): List[Session] = {
    val messages = loadMessagesFrom(folder.excavate.filter(_.endsWith(target)).toList)
    var sessions: List[Session] = Nil
    var sessionId = 0
    messages.foldLeft (new Session(sessionId, target)) {
      (session, message) => {
        var retSession = session
        message match {
          case x: AMSNSessionOpenMessage => {
            retSession = new Session(sessionId, target)
            sessions ::= retSession
          }
          case _ => null
        }
        retSession + message
      }
    }
    sessions.sortBy(_.messages(0).datetime).map({
      session =>
        sessionId += 1
        session.sessionId = sessionId
        session
    })
  }

  private def loadMessagesFrom(filePaths: List[String]): List[Message] = {
    try {
      val messages = filePaths flatMap convertMessageFromSource
      zipMessages(messages)
    } catch {
      case e: Exception => Nil
    }
  }

  private def convertMessageFromSource(targetFile: String): List[Message] = {
    // TODO Offline session open
    Source.fromInputStream(targetFile, Codec.UTF8.name).getLines().flatMap(
      line => line match {
        case AMSNSessionOpenMessage(time, from, content) => List(new AMSNSessionOpenMessage(time, from, content))
        case AMSNMessage(time, from, color, content) => List(new AMSNMessage(time, from, color, content))
        case AMSNSessionCloseMessage(time, from) => List(new AMSNSessionCloseMessage(time, from))
        case AMSNEmptyMessage(_) => List(new AMSNEmptyMessage)
        case _ => List(new AMSNWrapMessage(line))
      }
    ).toList
  }

  private def zipMessages(messages: List[Message]): List[Message] = {
    var retMessages: List[Message] = Nil
    val lastMessage = messages.reduceLeft {
      (last, curr) => {
        curr match {
          case _: AMSNWrapMessage | _: AMSNEmptyMessage => {
            last match {
              case z: AMSNSessionCloseMessage => new AMSNSessionCloseMessage(z.datetime, z.from, z.text + curr.text)
              case z: AMSNMessage => new AMSNMessage(z.datetime, z.from, z.color, z.text + curr.text)
              case _ => curr
            }
          }
          case _ => {
            retMessages ::= last
            curr
          }
        }
      }
    }
    (lastMessage :: retMessages).reverse
  }
}