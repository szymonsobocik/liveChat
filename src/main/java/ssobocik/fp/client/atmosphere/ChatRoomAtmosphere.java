package ssobocik.fp.client.atmosphere;

import ssobocik.fp.client.FClientUtils;
import ssobocik.fp.client.atmosphere.messages.ChatMessage;
import ssobocik.fp.client.atmosphere.messages.CheckConnectedUsersMessage;
import ssobocik.fp.client.atmosphere.messages.UserJoinedMessage;
import ssobocik.fp.client.view.atmosphere.ChatRoom;
import ssobocik.fp.dto.UserDTO;

import java.util.List;
import java.util.logging.Level;

/**
 * Atmosphere for private ChatRooms
 * @author szymon.sobocik
 */
public class ChatRoomAtmosphere extends BaseAtmosphere {

    private ChatRoom chatRoom;
    private UserDTO otherSpeaker;

    public ChatRoomAtmosphere(String room, ChatRoom chatRoom) {
        this.room = room;
        this.chatRoom = chatRoom;
    }

    public void setOtherSpeaker(UserDTO otherSpeaker) {
        this.otherSpeaker = otherSpeaker;
    }

    @Override
    protected BaseAtmosphereListener createAtmosphereListener() {
        return new ChatRoomAtmosphereListener();
    }

    @Override
    protected ChatMessage createChatMessage(String message) {
        ChatMessage chatMessage = super.createChatMessage(message);
        chatMessage.setRecipient(otherSpeaker);
        return chatMessage;
    }

    private class ChatRoomAtmosphereListener extends BaseAtmosphereListener {

        @Override
        public void onConnected(int heartbeat, String connectionID) {
            connected = true;
            logger.info("comet.connected [" + heartbeat + ", " + connectionID + "]");
            client.broadcast(new UserJoinedMessage(FClientUtils.getInstance().getSignedInUser()));
        }

        @Override
        public void onBeforeDisconnected() {
            logger.log(Level.INFO, "comet.beforeDisconnected");
            sendMessage("I'm leaving. Good bye.");
            client.broadcast(new CheckConnectedUsersMessage());
            connected = false;
        }

        @Override
        public void onDisconnected() {
            logger.info("comet.disconnected");
            FClientUtils.getInstance().setStatusDisconnected();
        }


        @Override
        public void onMessage(List<?> messages) {
            for (Object obj : messages) {
                logger.info("Received message: " + obj);
                if (obj instanceof UserJoinedMessage) {
                    UserJoinedMessage ujMessage = (UserJoinedMessage) obj;
                } else if (obj instanceof ChatMessage) {
                    chatRoom.receivedMessage((ChatMessage) obj);
                }
            }
        }
    }
}

