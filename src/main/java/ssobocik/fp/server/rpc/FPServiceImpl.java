package ssobocik.fp.server.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssobocik.fp.client.rpc.FPService;
import ssobocik.fp.dto.UserDTO;
import ssobocik.fp.exceptions.BadUserCredentialsException;
import ssobocik.fp.exceptions.ObjectNotValidException;
import ssobocik.fp.exceptions.UserNotFoundException;
import ssobocik.fp.server.bl.UsersBl;
import ssobocik.fp.server.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Serves as server-side for GWT RPC
 * Simple facade for furher services
 * This class is not transactional since it is invoked from rpc servlet,
 * so it only redirects request to real transactional service
 * @author szymon.sobocik
 */
@Service("fpService")
public class FPServiceImpl implements FPService {

    @Autowired
    FPServiceBl fpServiceBl;

    @Override
    public String helloWorld(String name) {
        return fpServiceBl.helloWorld(name);
    }

    @Override
    public UserDTO singIn(String username, String password) throws UserNotFoundException, BadUserCredentialsException {
        return fpServiceBl.singIn(username, password);
    }

    @Override
    public UserDTO createNewUser(String username, String password) throws ObjectNotValidException {
        return fpServiceBl.createNewUser(username, password);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return fpServiceBl.getAllUsers();
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) throws ObjectNotValidException {
        return fpServiceBl.saveUser(userDTO);
    }

    @Override
    public void removeUser(UserDTO userDTO) throws ObjectNotValidException, UserNotFoundException {
        fpServiceBl.removeUser(userDTO);
    }
}
