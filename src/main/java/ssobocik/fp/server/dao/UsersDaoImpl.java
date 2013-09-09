package ssobocik.fp.server.dao;

import org.springframework.stereotype.Repository;
import ssobocik.fp.server.domain.User;

import javax.persistence.Query;
import java.util.List;

/**
 * Dao layer for user management
 *
 * @author szymon.sobocik
 */
@Repository
public class UsersDaoImpl extends AbstractDaoImpl implements UsersDao {

    @Override
    public User findByUsername(String username) {
        Query query = em.createQuery("select u From User u" +
                " where u.username = :username ");
        query.setParameter("username", username);
        //noinspection unchecked
        return (User) getSingleResult(query.getResultList());
    }

    @Override
    public User mergeUser(User newUser) {
        User merge = em.merge(newUser);
        em.flush();
        return merge;
    }

    @Override
    public List<User> getAllUsers() {
        Query query = em.createQuery("select u from User u" +
                " order by u.username ");
        //noinspection unchecked
        return query.getResultList();
    }

    @Override
    public User findById(Integer idUser) {
        Query query = em.createQuery("select u from User u" +
                " where u.id = :idUser ");
        query.setParameter("idUser", idUser);
        //noinspection unchecked
        return (User) getSingleResult(query.getResultList());
    }

    @Override
    public boolean isUsernameFree(String username) {
        Query query = em.createQuery("select count(u.username) from User u " +
                " where u.username = :username ");
        query.setParameter("username", username);
        Long count = (Long) query.getSingleResult();
        return count == 0;
    }

    @Override
    public void remove(User user) {
        em.remove(user);
        em.flush();
    }

    @Override
    public boolean isUserInVersion(Integer idUser, Integer version) {
        Query query = em.createQuery("select count(u) from User u " +
                "where u.id = :idUser " +
                "and u.version = :version ");
        query.setParameter("idUser", idUser);
        query.setParameter("version", version);

        Long count = (Long) query.getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public boolean isUniqueUsername(Integer idUser, String username) {
        String jql = "select count(u) from User u " +
                "where u.username = :username ";
        if (idUser != null) {
            jql += "and u.id <> :idUser ";
        }

        Query query = em.createQuery(jql);
        query.setParameter("username", username);
        if (idUser != null) {
            //noinspection JpaQueryApiInspection
            query.setParameter("idUser", idUser);
        }

        Long count = (Long) query.getSingleResult();
        return count == 0;
    }

    @Override
    public boolean isUniqueNickname(Integer idUser, String nickname) {
        String jql = "select count(u) from User u " +
                "where u.nickname = :nickname ";
        if (idUser != null) {
            jql += "and u.id <> :idUser ";
        }

        Query query = em.createQuery(jql);
        query.setParameter("nickname", nickname);
        if (idUser != null) {
            //noinspection JpaQueryApiInspection
            query.setParameter("idUser", idUser);
        }

        Long count = (Long) query.getSingleResult();
        return count == 0;
    }
}
