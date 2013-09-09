package ssobocik.fp.dto;

import java.io.Serializable;

/**
 * @author szymon.sobocik
 */
public class UserStatusDTO implements Serializable {

    private UserDTO userDTO;
    private StatusDTO statusDTO;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public StatusDTO getStatusDTO() {
        return statusDTO;
    }

    public void setStatusDTO(StatusDTO statusDTO) {
        this.statusDTO = statusDTO;
    }
}
