package org.hibernate.bugs.entities.user;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.bugs.entities.BaseEntity;

@Entity
@Table(name = "T_USER")
public abstract class User extends BaseEntity<UserId> {

    @EmbeddedId
    private  UserId userId;

    public User(UserId userId) {
        this.userId = userId;
    }

    public User() {
        this.userId = null;
    }

    @Override
    public UserId getId(){
        return this.userId;
    }
}
