package ssobocik.fp.client.view.user.manage;

import ssobocik.fp.dto.UserDTO;

/**
 * @author szymon.sobocik
 */
public interface UserSelectedHandler {
    void selectedUser(UserDTO userDTO);
}
