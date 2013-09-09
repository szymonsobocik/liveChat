package ssobocik.fp.server.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssobocik.fp.client.atmosphere.messages.ChatMessage;
import ssobocik.fp.server.dao.ChatDao;
import ssobocik.fp.server.domain.Message;
import ssobocik.fp.server.domain.User;

/**
 * @author szymon.sobocik
 */
@Service("chatBl")
@Transactional(rollbackFor = Exception.class)
public class ChatBlImpl implements ChatBl {

    @Autowired
    private ChatDao chatDao;

    @Autowired
    private UsersBl usersBl;

    @Override
    public void saveMessage(ChatMessage chatMessage) {
        Message newMessage = new Message();
        newMessage.setMessage(chatMessage.getMessage());
        newMessage.setDate(chatMessage.getTime());

        if (chatMessage.getAuthor() != null){
            User author = usersBl.findById(chatMessage.getAuthor().getId());
            newMessage.setAuthor(author);
        }

        if (chatMessage.getRecipient() != null){
            User recipient = usersBl.findById(chatMessage.getRecipient().getId());
            newMessage.setRecipient(recipient);
        }

        chatDao.mergeMessage(newMessage);
    }
}
