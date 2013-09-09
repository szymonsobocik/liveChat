package ssobocik.fp.client.view.atmosphere;

import ssobocik.fp.client.atmosphere.ChatRoomAtmosphere;

/**
 * @author szymon.sobocik
 */
public class ChatRoom extends ChatRoomBase {

    private ChatRoomAtmosphere chatRoomAtmosphere;

    @Override
    protected void sendMessage(String message) {
        chatRoomAtmosphere.sendMessage(message);
    }

    public void setChatRoomAtmosphere(ChatRoomAtmosphere chatRoomAtmosphere) {
        this.chatRoomAtmosphere = chatRoomAtmosphere;
    }
}

