package ssobocik.fp.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ssobocik.fp.dto.UserDTO;
import ssobocik.fp.exceptions.BadUserCredentialsException;
import ssobocik.fp.exceptions.ObjectNotValidException;
import ssobocik.fp.exceptions.UserNotFoundException;

import java.util.List;

/**
 * @author szymon.sobocik
 */
@RemoteServiceRelativePath("rpc/fpService")
public interface FPService extends RemoteService {

    String helloWorld(String name);

    UserDTO singIn(String username, String password) throws UserNotFoundException, BadUserCredentialsException;

    UserDTO createNewUser(String username, String password) throws ObjectNotValidException;

    List<UserDTO> getAllUsers();

    UserDTO saveUser(UserDTO userDTO) throws ObjectNotValidException;

    void removeUser(UserDTO userDTO) throws ObjectNotValidException, UserNotFoundException;
}
