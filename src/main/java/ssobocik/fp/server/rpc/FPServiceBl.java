package ssobocik.fp.server.rpc;

import ssobocik.fp.dto.UserDTO;
import ssobocik.fp.exceptions.BadUserCredentialsException;
import ssobocik.fp.exceptions.ObjectNotValidException;
import ssobocik.fp.exceptions.UserNotFoundException;
import ssobocik.fp.server.security.CheckVersionUser;

import java.util.List;

/**
 * @author szymon.sobocik
 */
public interface FPServiceBl {

    String helloWorld(String name);

    UserDTO singIn(String username, String password) throws UserNotFoundException, BadUserCredentialsException;

    UserDTO createNewUser(String username, String password) throws ObjectNotValidException;

    List<UserDTO> getAllUsers();

    @CheckVersionUser
    UserDTO saveUser(UserDTO userDTO) throws ObjectNotValidException;

    @CheckVersionUser
    void removeUser(UserDTO userDTO) throws ObjectNotValidException, UserNotFoundException;
}
