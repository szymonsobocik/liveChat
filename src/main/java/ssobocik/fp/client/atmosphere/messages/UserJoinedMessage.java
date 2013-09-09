package ssobocik.fp.client.atmosphere.messages;

import ssobocik.fp.dto.UserDTO;

import java.io.Serializable;

/**
 * @author szymon.sobocik
 */
public class UserJoinedMessage implements Serializable {
    private UserDTO userDTO;

    public UserJoinedMessage() {
    }

    public UserJoinedMessage(UserDTO signedInUser) {
        this.userDTO = signedInUser;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
