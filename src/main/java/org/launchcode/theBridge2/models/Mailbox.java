package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class Mailbox extends AbstractEntity{

    @NotBlank (message = "Date sent cannot be blank")
    @NotNull(message = "Date sent cannot be null")
    private String dateSent;



    @OneToOne
    private User sender;


    @OneToOne
    private User recipient;

    @Size(max = 250)
    private String subject;

    @NotBlank (message = "Message cannot be blank")
    @NotNull(message = "Message cannot be null")
    @Size(max=2500)
    private String message;

    private boolean isNew;


    public Mailbox(){}

    public Mailbox(String dateSent, User sender, User recipient, String subject,
                   String message){

        this.dateSent = dateSent;
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.isNew = true;
    }


    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
