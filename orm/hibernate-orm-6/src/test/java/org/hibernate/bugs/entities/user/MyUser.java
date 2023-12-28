package org.hibernate.bugs.entities.user;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.bugs.entities.relation.AddressUserRelation;

@Entity
public class MyUser extends User {

    @OneToMany(mappedBy = "addressUserRelationId.myUser")
    private final List<AddressUserRelation> addressUserRelations = new ArrayList<>();

    public MyUser(UserId userId) {
        super(userId);
    }

    public MyUser() {
    }



}
