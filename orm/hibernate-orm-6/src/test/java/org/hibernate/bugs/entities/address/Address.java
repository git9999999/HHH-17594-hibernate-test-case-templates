package org.hibernate.bugs.entities.address;

import static org.hibernate.bugs.entities.BooleanUtil.not;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import org.hibernate.bugs.entities.BaseEntity;
import org.hibernate.bugs.entities.address.AddressId;
import org.hibernate.bugs.entities.address.MyAddress;

@Entity
@Table(name = "T_ADDRESS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ADDRESS_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Address  extends BaseEntity<AddressId> {

    @EmbeddedId
    private final AddressId addressId;

    public Address(AddressId addressId) {
        this.addressId = addressId;
    }

    public Address() {
        this.addressId = null;
    }

    public AddressId getAddressId() {
        return addressId;
    }

    public MyAddress castToMyAddress() {
        if (not(isMyAddress())) {
            throw new RuntimeException("COULD_NOT_CAST" + this.addressId.getValue() + "' to MyAddress");
        }
        return (MyAddress) this;
    }

    public boolean isMyAddress() {
        return (this instanceof MyAddress);
    }


}
