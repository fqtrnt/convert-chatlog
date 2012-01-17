/**
 *
 */
package com.chatlogs.msn

import scala.collection.immutable.List
import com.chatlogs.core.MessageReader
import com.chatlogs.core.Session
import scala.xml.XML
import com.chatlogs.core.Message
import scala.xml.Elem

/**
 * @author fqtrnt [2012/01/16]
 * @version 1.0.0
 *
 */
class MSNMessageReader(folder: String, fileName: String) extends MessageReader {

  def sessions(): List[Session] = {
    val doc = XML.load({
      if (folder.endsWith("/")) folder + fileName
      else folder + "/" + fileName
    })
    val messages: List[MSNMessage] = extractMessage(doc)
    pack2sessions(messages).sortBy(_.messages(0).datetime)
  }

  private def extractMessage(doc: Elem): List[MSNMessage] = {
    (List[MSNMessage]() /: (doc \ "Message")) {
      (list, node) =>
        val datetime = (node \ "@DateTime").toString
        val from = (node \ "From" \ "User" \ "@FriendlyName").toString
        val to = (node \ "To" \ "User" \ "@FriendlyName").toString
        val style = (node \ "Text" \ "@Style").toString
        val text = (node \ "Text").text
        val sessionId = (node \ "@SessionID").toString.toInt
        new MSNMessage(datetime, from, to, style, text, sessionId) +: list
    }
  }

  private def pack2sessions(messages: List[MSNMessage]): List[Session] = {
    var sessions: List[Session] = Nil
    messages.foldLeft (new Session(0, fileName)) {
      (session, message) =>
        var retSession = session
        if (session.sessionId != message.sessionId) {
          retSession = new Session(message.sessionId, fileName)
          sessions ::= retSession
        }
        retSession + message
    }
    sessions
  }
}