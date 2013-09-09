package ssobocik.fp.client.atmosphere;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import ssobocik.fp.client.FClientUtils;
import ssobocik.fp.client.atmosphere.messages.*;
import ssobocik.fp.client.events.ClearUserStatusesEvent;
import ssobocik.fp.client.events.ConnectToChatEvent;
import ssobocik.fp.client.events.LoadAllUsersEvent;
import ssobocik.fp.client.events.RefreshUserStatusEvent;
import ssobocik.fp.client.events.initEvents.InitEvents;
import ssobocik.fp.dto.UserDTO;
import ssobocik.fp.dto.UserStatusDTO;

import java.util.List;
import java.util.logging.Level;

/**
 * Main atmosphere client handler.
 * Dispatches all messages in shoutbox and some extra messages
 * like requests to refresh user statuses and inormations about new users.
 *
 * @author szymon.sobocik
 */
public class ShoutboxAtmosphere extends BaseAtmosphere {

    private static ShoutboxAtmosphere ref;

    public synchronized static ShoutboxAtmosphere getInstance() {
        if (ref == null) {
            ref = new ShoutboxAtmosphere();
        }
        return ref;
    }

    public ShoutboxAtmosphere() {
        this.room = "shoutbox";
    }

    @Override
    protected BaseAtmosphereListener createAtmosphereListener() {
        return new ShoutboxAtmosphereListener();
    }

    public void informAboutMyStatus() {
        if (canBroadcast()) {
            UserStatusDTO userStatusDTO = new UserStatusDTO();
            userStatusDTO.setUserDTO(FClientUtils.getInstance().getSignedInUser());
            userStatusDTO.setStatusDTO(FClientUtils.getInstance().getStatus());
            logger.info("Informing about my status: " + userStatusDTO.getUserDTO().getUsername());
            client.broadcast(new UserStatusMessage(userStatusDTO));
        }
    }

    public void inviteUserToChat(String roomId, UserDTO userToTalkTo) {
        if (canBroadcast()) {
            InviteUserMessage inviteUserMessage = new InviteUserMessage();
            inviteUserMessage.setRoom(roomId);
            inviteUserMessage.setInvitedUser(userToTalkTo);
            inviteUserMessage.setHost(FClientUtils.getInstance().getSignedInUser());
            client.broadcast(inviteUserMessage);
        }
    }

    public void checkConnectedUsers() {
        if (canBroadcast()) {
            client.broadcast(new CheckConnectedUsersMessage());
        }
    }

    public void refreshUserListRequest(){
        if (canBroadcast()){
            client.broadcast(new RefreshUserListRequestMessage());
        }
    }

    private class ShoutboxAtmosphereListener extends BaseAtmosphereListener {

        @Override
        public void onConnected(int heartbeat, String connectionID) {
            connected = true;
            logger.info("comet.connected [" + heartbeat + ", " + connectionID + "]");
            FClientUtils.getInstance().setStatusConnected();
            client.broadcast(new UserJoinedMessage(FClientUtils.getInstance().getSignedInUser()));
            checkConnectedUsers();
        }

        @Override
        public void onBeforeDisconnected() {
            logger.log(Level.INFO, "comet.beforeDisconnected");
            sendMessage("I'm leaving. Good bye. [USER DISCONNECTED]");
            checkConnectedUsers();
        }

        @Override
        public void onDisconnected() {
            connected = false;
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
                } else if (obj instanceof CheckConnectedUsersMessage) {
                    InitEvents.EVENT_BUS.fireEvent(new ClearUserStatusesEvent());
                    Scheduler.get().scheduleDeferred(new Command() {
                        @Override
                        public void execute() {
                            informAboutMyStatus();
                        }
                    });
                } else if (obj instanceof UserStatusMessage) {
                    InitEvents.EVENT_BUS.fireEvent(new RefreshUserStatusEvent(((UserStatusMessage) obj).getUserStatusDTO()));
                } else if (obj instanceof InviteUserMessage) {
                    if (FClientUtils.getInstance().isConnectedThisUser(((InviteUserMessage) obj).getInvitedUser().getId())) {
                        InitEvents.EVENT_BUS.fireEvent(new ConnectToChatEvent((InviteUserMessage) obj));
                    }
                } else if (obj instanceof RefreshUserListRequestMessage){
                    InitEvents.EVENT_BUS.fireEvent(new LoadAllUsersEvent());
                }
            }
        }
    }
}
