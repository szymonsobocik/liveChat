package ssobocik.fp.client.atmosphere.messages;

import ssobocik.fp.dto.UserDTO;

import java.io.Serializable;

/**
 * Invitation for another user to private chat
 * @author szymon.sobocik
 */
public class InviteUserMessage implements Serializable {
    private String room;
    private UserDTO invitedUser;
    /**
     * User who initiates the chat
     */
    private UserDTO host;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public UserDTO getInvitedUser() {
        return invitedUser;
    }

    public void setInvitedUser(UserDTO invitedUser) {
        this.invitedUser = invitedUser;
    }

    public UserDTO getHost() {
        return host;
    }

    public void setHost(UserDTO host) {
        this.host = host;
    }
}
