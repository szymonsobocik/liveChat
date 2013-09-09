package ssobocik.fp.server.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ssobocik.fp.exceptions.BadUserCredentialsException;
import ssobocik.fp.exceptions.ObjectNotValidException;
import ssobocik.fp.exceptions.UserNotFoundException;
import ssobocik.fp.server.dao.UsersDao;
import ssobocik.fp.server.domain.User;

import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Manages users.
 *
 * @author szymon.sobocik
 */
@Service("usersBl")
@Transactional(rollbackFor = Exception.class)
public class UsersBlImpl implements UsersBl {

    private ResourceBundle messages = ResourceBundle.getBundle("ssobocik.fp.server.messagesBundle");

    private static final boolean DEFAULT_IS_ADMIN = true;

    @Autowired
    private UsersDao usersDao;

    @Override
    public User signIn(String username, String password) throws UserNotFoundException, BadUserCredentialsException {
        checkArgument(!StringUtils.isEmpty(username), messages.getString("username.notEmpty"));

        //todo here start application security context
        User byUsername = findByUsername(username);
        if (!byUsername.getPassword().equals(password)){
            throw new BadUserCredentialsException(messages.getString("user.wrongUsernameOrPassword"));
        }
        return byUsername;
    }


    @Override
    public User createNewUser(String username, String password) throws ObjectNotValidException {
        if (!usersDao.isUsernameFree(username)) {
            throw new ObjectNotValidException(MessageFormat.format(messages.getString("user.notUnique.username"), username));
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setNickname(username);
        newUser.setPassword(password);
        newUser.setAdmin(DEFAULT_IS_ADMIN);
        newUser = mergeUser(newUser);
        return newUser;
    }

    @Override
    public List<User> getAllUsers() {
        return usersDao.getAllUsers();
    }

    @Override
    public User mergeUser(User user) throws ObjectNotValidException {
        validate(user);
        return usersDao.mergeUser(user);
    }

    private void validate(User user) throws ObjectNotValidException {
        if (!usersDao.isUniqueUsername(user.getId(), user.getUsername())) {
            throw new ObjectNotValidException(MessageFormat.format(messages.getString("user.notUnique.username"), user.getUsername()));
        }
        if (!usersDao.isUniqueNickname(user.getId(), user.getNickname())) {
            throw new ObjectNotValidException(MessageFormat.format(messages.getString("user.notUnique.nickname"), user.getNickname()));
        }
    }

    @Override
    public User findById(Integer id) {
        checkArgument(id != null, messages.getString("user.id.null"));
        User byId = usersDao.findById(id);
        usersDao.detach(byId);
        return byId;
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        User user = usersDao.findByUsername(username);
        checkUserFound(user);
        return user;
    }

    private void checkUserFound(User user) throws UserNotFoundException {
        if (user == null) {
            throw new UserNotFoundException(messages.getString("user.notFound"));
        }
    }

    @Override
    public void removeUser(Integer idUser) throws UserNotFoundException {
        User user = usersDao.findById(idUser);
        checkUserFound(user);
        usersDao.remove(user);
    }

    @Override
    public void checkUserInVersion(Integer idUser, Integer version) throws ObjectNotValidException {
        if (!usersDao.isUserInVersion(idUser, version)) {
            throw new ObjectNotValidException(messages.getString("user.modified"));
        }
    }
}
