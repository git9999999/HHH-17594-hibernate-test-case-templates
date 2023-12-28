package org.hibernate.bugs.entities.relation;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.bugs.entities.BaseEntity;

@Entity
@Table(name = "T_ADR_USER_REL")
public class AddressUserRelation extends BaseEntity<AddressUserRelationId> {

    @EmbeddedId
    private AddressUserRelationId addressUserRelationId;

    public AddressUserRelation(AddressUserRelationId addressUserRelationId) {
        this.addressUserRelationId = addressUserRelationId;
    }

    public AddressUserRelation() {
        this.addressUserRelationId = null;
    }

    @Override
    public AddressUserRelationId getId() {
        return this.addressUserRelationId;
    }

}
