/**
 *
 */
package com.chatlogs.msn
import com.chatlogs.core.MessageTimestamp
import com.chatlogs.core.MessageType


/**
 * @author fqtrnt [2012/01/21]
 * @version 1.0.0
 *
 */
class Invitation(override val datetime: MessageTimestamp, override val from: String, val file: String,
                 override val style: String, override val text: String, override val sessionId: Int)
                 extends MSNMessage(datetime, from, null, style, text, sessionId) {
}

/**
 * @author fqtrnt [2012/01/21]
 * @version 1.0.0
 *
 */
class InvitationResponse(override val datetime: MessageTimestamp, override val from: String, val file: String,
                 override val style: String, override val text: String, override val sessionId: Int)
                 extends MSNMessage(datetime, from, null, style, text, sessionId) {
}