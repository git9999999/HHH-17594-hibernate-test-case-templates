package org.hibernate.bugs.entities.address;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.bugs.entities.relation.AddressUserRelation;

@Entity
public class MyAddress extends Address {

    @OneToMany(mappedBy = "addressUserRelationId.myAddress")
    private  Set<AddressUserRelation> addressUserRelations = new HashSet<>();


    public MyAddress(AddressId addressId) {
        super(addressId);
    }

    public MyAddress() {
    }

    public Set<AddressUserRelation> getAddressUserRelations() {
        return addressUserRelations;
    }

    @Override
    public AddressId getId() {
        return this.getAddressId();
    }

}
