package ssobocik.fp.client.atmosphere.messages;

import ssobocik.fp.dto.UserStatusDTO;

import java.io.Serializable;

/**
 * Informs other users about my status
 * @author szymon.sobocik
 */
public class UserStatusMessage implements Serializable {

    private UserStatusDTO userStatusDTO;

    public UserStatusMessage() {
    }

    public UserStatusMessage(UserStatusDTO userStatusDTO) {
        this.userStatusDTO = userStatusDTO;
    }

    public UserStatusDTO getUserStatusDTO() {
        return userStatusDTO;
    }

    public void setUserStatusDTO(UserStatusDTO userStatusDTO) {
        this.userStatusDTO = userStatusDTO;
    }
}
