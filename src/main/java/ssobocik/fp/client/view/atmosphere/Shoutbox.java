package ssobocik.fp.client.view.atmosphere;

import ssobocik.fp.client.atmosphere.ShoutboxAtmosphere;

/**
 * Main chat room
 *
 * @author szymon.sobocik
 */
public class Shoutbox extends ChatRoomBase {

    @Override
    protected void sendMessage(String message) {
        ShoutboxAtmosphere.getInstance().sendMessage(messageInput.getText());
    }

}
