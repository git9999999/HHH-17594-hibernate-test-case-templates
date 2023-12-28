package org.hibernate.bugs.entities.message;

import static org.hibernate.bugs.entities.IdPrefix.ADDRESS;
import static org.hibernate.bugs.entities.IdPrefix.INBOX_MESSAGE;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import org.hibernate.bugs.entities.SingleStringValueHolderId;


@MappedSuperclass
@Embeddable
@AttributeOverride(name = "value", column = @Column(name = "ADDRESS_ID"))
public class MessageId extends SingleStringValueHolderId implements Serializable {

    public MessageId() {
        super(INBOX_MESSAGE);
    }

    public MessageId(String value) {
        super(INBOX_MESSAGE, value);
    }

    public static MessageId addressIdOf(String value) {
        return new MessageId(value);
    }

}
