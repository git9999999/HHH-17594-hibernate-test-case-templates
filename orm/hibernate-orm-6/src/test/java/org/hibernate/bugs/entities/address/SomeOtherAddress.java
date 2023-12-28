package org.hibernate.bugs.entities.address;

import jakarta.persistence.Entity;
import org.hibernate.bugs.entities.address.Address;
import org.hibernate.bugs.entities.address.AddressId;

@Entity
public class SomeOtherAddress extends Address {

    public SomeOtherAddress(AddressId addressId) {
        super(addressId);
    }

    public SomeOtherAddress() {
    }


    @Override
    public AddressId getId() {
        return this.getAddressId();
    }

}
