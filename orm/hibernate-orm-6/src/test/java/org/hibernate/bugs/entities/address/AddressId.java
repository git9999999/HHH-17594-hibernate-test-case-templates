package org.hibernate.bugs.entities.address;

import static org.hibernate.bugs.entities.IdPrefix.ADDRESS;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import org.hibernate.bugs.entities.SingleStringValueHolderId;


@MappedSuperclass
@Embeddable
@AttributeOverride(name = "value", column = @Column(name = "ADDRESS_ID"))
public class AddressId extends SingleStringValueHolderId implements Serializable {

    public AddressId() {
        super(ADDRESS);
    }

    public AddressId(String value) {
        super(ADDRESS, value);
    }

    public static AddressId addressIdOf(String value) {
        return new AddressId(value);
    }

}
