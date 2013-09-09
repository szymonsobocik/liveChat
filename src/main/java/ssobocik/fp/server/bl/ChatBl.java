package ssobocik.fp.server.bl;

import ssobocik.fp.client.atmosphere.messages.ChatMessage;

/**
 * Records all messages
 *
 * @author szymon.sobocik
 */
public interface ChatBl {

    void saveMessage(ChatMessage chatMessage);
}
