package org.hibernate.bugs.entities.relation;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import org.hibernate.bugs.entities.address.MyAddress;
import org.hibernate.bugs.entities.user.MyUser;

@Embeddable
public class AddressUserRelationId implements Serializable {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private final MyUser myUser;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ADDRESS_ID")
    private final MyAddress myAddress;

    protected AddressUserRelationId() {
        this.myUser = null;
        this.myAddress = null;
    }

    public AddressUserRelationId(MyUser myUser, MyAddress myAddress) {
        this.myUser = myUser;
        this.myAddress = myAddress;
    }

    public MyUser getMyUser() {
        return myUser;
    }

    public MyAddress getMyAddress() {
        return myAddress;
    }



}
