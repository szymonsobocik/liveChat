package ssobocik.fp.server.dao;

import ssobocik.fp.server.domain.Message;

/**
 * @author szymon.sobocik
 */
public interface ChatDao extends AbstractDao {

    public Message mergeMessage(Message message);
}
