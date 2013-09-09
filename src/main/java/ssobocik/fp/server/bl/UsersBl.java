package ssobocik.fp.server.bl;


import ssobocik.fp.exceptions.BadUserCredentialsException;
import ssobocik.fp.exceptions.ObjectNotValidException;
import ssobocik.fp.exceptions.UserNotFoundException;
import ssobocik.fp.server.domain.User;

import java.util.List;

/**
 * @author szymon.sobocik
 */
public interface UsersBl {
    User signIn(String username, String password) throws UserNotFoundException, BadUserCredentialsException;

    /**
     * Creates and saves new user with same username and nickname
     *
     * @return saved instance of user
     */
    User createNewUser(String username, String password) throws ObjectNotValidException;

    List<User> getAllUsers();

    User mergeUser(User user) throws ObjectNotValidException;

    User findById(Integer id);

    User findByUsername(String username) throws UserNotFoundException;

    void removeUser(Integer idUser) throws UserNotFoundException;

    void checkUserInVersion(Integer idUser, Integer version) throws ObjectNotValidException;
}
