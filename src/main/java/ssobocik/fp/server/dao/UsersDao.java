package ssobocik.fp.server.dao;

import ssobocik.fp.server.domain.User;

import java.util.List;

/**
 * @author szymon.sobocik
 */
public interface UsersDao extends AbstractDao {
    User findByUsername(String username);

    User mergeUser(User newUser);

    List<User> getAllUsers();

    User findById(Integer idUser);

    boolean isUsernameFree(String username);

    void remove(User user);

    boolean isUserInVersion(Integer idUser, Integer version);

    boolean isUniqueUsername(Integer idUser, String username);

    boolean isUniqueNickname(Integer idUser, String nickname);
}
