package ssobocik.fp.server.dao;

import org.springframework.stereotype.Repository;
import ssobocik.fp.server.domain.Message;

/**
 * @author szymon.sobocik
 */
@Repository
public class ChatDaoImpl extends AbstractDaoImpl implements ChatDao {
    @Override
    public Message mergeMessage(Message message) {
        Message merge = em.merge(message);
        em.flush();
        return merge;
    }
}
