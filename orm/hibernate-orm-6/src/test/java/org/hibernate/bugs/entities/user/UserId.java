package org.hibernate.bugs.entities.user;

import static org.hibernate.bugs.entities.IdPrefix.ADDRESS;
import static org.hibernate.bugs.entities.IdPrefix.USER;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import org.hibernate.bugs.entities.SingleStringValueHolderId;


@MappedSuperclass
@Embeddable
@AttributeOverride(name = "value", column = @Column(name = "USER_ID"))
public class UserId extends SingleStringValueHolderId implements Serializable {

    public UserId() {
        super(USER);
    }

    public UserId(String value) {
        super(USER, value);
    }


}
