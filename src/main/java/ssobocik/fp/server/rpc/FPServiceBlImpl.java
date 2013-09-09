package ssobocik.fp.server.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
 * Facade for all RPC request from client side
 * Translates from domain to DTO objects.
 * @author szymon.sobocik
 */
@Service("fpServiceBl")
@Transactional(rollbackFor = Exception.class)
public class FPServiceBlImpl implements FPServiceBl {
    private ResourceBundle messages = ResourceBundle.getBundle("ssobocik.fp.server.messagesBundle");

    @Autowired
    private UsersBl usersBl;

    @Override
    public String helloWorld(String name) {
        return "Witaj " + name;
    }

    @Override
    public UserDTO singIn(String username, String password) throws UserNotFoundException, BadUserCredentialsException {
        User user = usersBl.signIn(username, password);
        return createDTOUser(user);
    }

    @Override
    public UserDTO createNewUser(String username, String password) throws ObjectNotValidException {
        User newUser = usersBl.createNewUser(username, password);
        return createDTOUser(newUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> allUsers = usersBl.getAllUsers();
        return createDTOUserList(allUsers);
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) throws ObjectNotValidException {
        if (userDTO == null) {
            throw new ObjectNotValidException(messages.getString("user.notNull"));
        }

        User user = null;
        if (userDTO.getId() != null) {
            //user already saved in DB
            user = usersBl.findById(userDTO.getId());
        } else {
            user = new User();
        }

        user = fillDomainUser(userDTO, user);
        user = usersBl.mergeUser(user);
        return createDTOUser(user);
    }

    @Override
    public void removeUser(UserDTO userDTO) throws ObjectNotValidException, UserNotFoundException {
        if (userDTO.getId() == null){
            throw new ObjectNotValidException(messages.getString("user.id.null"));
        }
        usersBl.removeUser(userDTO.getId());
    }

    private User fillDomainUser(UserDTO userDTO, User user) {
        user.setUsername(userDTO.getUsername());
        user.setNickname(userDTO.getNickname());
        user.setAdmin(userDTO.isAdmin());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    private List<UserDTO> createDTOUserList(List<User> allUsers) {
        ArrayList<UserDTO> allUsersDTO = new ArrayList<UserDTO>();
        for (User user : allUsers) {
            allUsersDTO.add(createDTOUser(user));
        }
        return allUsersDTO;
    }

    private UserDTO createDTOUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setVersion(user.getVersion());
        userDTO.setUsername(user.getUsername());
        userDTO.setNickname(user.getNickname());
        userDTO.setPassword(user.getPassword());
        userDTO.setAdmin(user.isAdmin());

        return userDTO;
    }
}
