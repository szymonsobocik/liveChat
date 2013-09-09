package ssobocik.fp.client.atmosphere.messages;

import ssobocik.fp.dto.UserDTO;

import java.io.Serializable;
import java.util.Date;

/**
 * Basic message sent during chatting
 *
 * @author szymon.sobocik
 */
public class ChatMessage implements Serializable {

    private UserDTO author;
    private String message;
    private Date time;
    /**
     * Set only if this is private chat
     */
    private UserDTO recipient;

    public ChatMessage() {
    }

    public ChatMessage(UserDTO author, String data) {
        this.author = author;
        this.message = data;
        this.time = new Date();
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public UserDTO getRecipient() {
        return recipient;
    }

    public void setRecipient(UserDTO recipient) {
        this.recipient = recipient;
    }
}
