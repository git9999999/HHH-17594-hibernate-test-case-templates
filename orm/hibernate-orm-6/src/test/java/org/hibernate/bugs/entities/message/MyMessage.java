package org.hibernate.bugs.entities.message;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.bugs.entities.BaseEntity;
import org.hibernate.bugs.entities.address.Address;
import org.hibernate.bugs.entities.address.AddressId;

@Entity
public class MyMessage extends BaseEntity<MessageId> {

    @EmbeddedId
    private final MessageId messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ADDRESS_ID")
    private Address receiverAddress;

    public MyMessage(Address receiverAddress) {
        this.receiverAddress = receiverAddress;
        this.messageId = new MessageId();
    }

    public MyMessage() {
        this.receiverAddress = null;
        this.messageId = null;
    }

    public Address getReceiverAddress() {
        return receiverAddress;
    }

    @Override
    public MessageId getId() {
        return this.getId();
    }

}
