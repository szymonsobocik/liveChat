package ssobocik.fp.client.atmosphere;

import org.atmosphere.gwt.client.AtmosphereGWTSerializer;
import org.atmosphere.gwt.client.SerialTypes;
import ssobocik.fp.client.atmosphere.messages.*;

/**
 * Serializes messages from javascript to java on server side
 * User by atmosphere framework
 *
 * @author szymon.sobocik
 */

@SerialTypes(value = {
        ChatMessage.class,
        CheckConnectedUsersMessage.class,
        InviteUserMessage.class,
        RefreshUserListRequestMessage.class,
        UserJoinedMessage.class,
        UserStatusMessage.class})
public abstract class ChatMessageSerializer extends AtmosphereGWTSerializer {
}
