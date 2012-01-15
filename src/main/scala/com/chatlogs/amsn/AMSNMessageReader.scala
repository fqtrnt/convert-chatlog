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

class AMSNMessageReader(target: String, sessionIdentifier: String= "") extends MessageReader {
  def sessions(): List[Session] = {
    val messages = loadMessagesFrom(target.excavate.filter(_.endsWith(sessionIdentifier)).toList)
    var sessions: List[Session] = Nil
    var sessionId = 0
    messages.foldLeft (new Session(sessionId, sessionIdentifier)) {
      (session, message) => {
        var retSession = session
        message match {
          case x: AMSNSessionOpenMessage => {
            sessionId += 1
            retSession = new Session(sessionId, sessionIdentifier)
            sessions ::= retSession
          }
          case _ => null
        }
        retSession + message
      }
    }
    sessions.sortBy(_.messages(0).datetime)
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
    val sessionOpen = """\|\"LRED\[Conversation started on \|\"LTIME(\d*)\]""".r
    val conferenceOpen = """\|\"LRED\[(.*) has entered into a conference on \|\"LTIME(\d*)(.*)\]""".r
    val chat = """\|\"LGRA\[\|\"LTIME(\d*) \] \|\"LITA(.*) :\|\"LC(\w*) (.*)""".r
    val transferFile = """\|\"LGRA\|\"LTIME(\d*) \|\"LGRE(.*)""".r
    val sessionClose = """\|\"LRED\[You have closed the window on (.*)\]""".r
    val conferenceClose = """\|\"LRED\[(.*) has closed the window on (.*)\]""".r
    val emptyList = """^\s*$""".r
    Source.fromInputStream(targetFile, Codec.UTF8.name).getLines().flatMap(
      line => line match {
        case sessionOpen(time) => List(new AMSNSessionOpenMessage(time))
        case conferenceOpen(from, time, content) => List(new AMSNSessionOpenMessage(time, from, content))
        case chat(time, from, color, content) => List(new AMSNMessage(time, from, color, content))
        case transferFile(time, content) => List(new AMSNMessage(time, null, null, content))
        case sessionClose(time) => List(new AMSNSessionCloseMessage(time))
        case conferenceClose(from, time) => List(new AMSNSessionCloseMessage(time, from))
        case emptyList() => List(new AMSNEmptyMessage)
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
              case z: AMSNSessionCloseMessage => new AMSNSessionCloseMessage(z.datetime, z.from, z.color, z.text + curr.text)
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