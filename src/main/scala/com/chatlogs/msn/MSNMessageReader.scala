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
import scala.xml.Node

/**
 * @author fqtrnt [2012/01/16]
 * @version 1.0.0
 *
 */
class MSNMessageReader extends MessageReader {

  def read(folder: String, target: String): List[Session] = {
    val doc = XML.load({
      if (folder.endsWith("/")) folder + target
      else folder + "/" + target
    })
    val messages: List[MSNMessage] = extractMessage(doc)
    pack2sessions(messages, target).sortBy(_.messages(0).datetime)
  }

  private def extractMessage(doc: Elem): List[MSNMessage] = {
    var messages = (List[MSNMessage]() /: (doc \ "Message")) {
      (list, node) =>
        val to = (node \ "To" \ "User" \ "@FriendlyName").toString
        val (datetime, from, style, text, sessionId) = fetchBaseXmlValue(node)
        new MSNMessage(datetime, from, to, style, text, sessionId) +: list
    }
    messages = (messages /: ((doc \ "Invitation") ++ (doc \ "InvitationResponse"))) {
      (list, node) =>
        val file = (node \ "File").text
        val (datetime, from, style, text, sessionId) = fetchBaseXmlValue(node)
        node.label match {
          case "Invitation" => new Invitation(datetime, from, file, style, text, sessionId) +: list
          case "InvitationResponse" => new InvitationResponse(datetime, from, file, style, text, sessionId) +: list
        }
    }
    messages.sortBy(_.sessionId)
  }

  private def fetchBaseXmlValue(node: Node): (String, String, String, String, Int) = {
    val datetime = (node \ "@DateTime").toString
    val from = (node \ "From" \ "User" \ "@FriendlyName").toString
    val style = (node \ "Text" \ "@Style").toString
    val text = (node \ "Text").text
    val sessionId = (node \ "@SessionID").toString.toInt
    (datetime, from, style, text, sessionId)
  }

  private def pack2sessions(messages: List[MSNMessage], target: String): List[Session] = {
    var sessions: List[Session] = Nil
    messages.foldLeft (new Session(0, target)) {
      (session, message) =>
        var retSession = session
        if (session.sessionId != message.sessionId) {
          retSession = new Session(message.sessionId, target)
          sessions ::= retSession
        }
        retSession + message
    }
    sessions
  }
}