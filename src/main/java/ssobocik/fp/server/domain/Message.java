package ssobocik.fp.server.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author szymon.sobocik
 */
@Entity
@Table(name = "FPMessage")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "message", length = 10240)
    private String message;

    @Column(name = "messageDate")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "authorUserID")
    private User author;

    @ManyToOne
    @JoinColumn(name = "recipientUserID")
    private User recipient;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
}
