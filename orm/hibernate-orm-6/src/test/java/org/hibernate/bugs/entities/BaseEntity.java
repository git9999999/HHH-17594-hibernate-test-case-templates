package org.hibernate.bugs.entities;


import static org.hibernate.bugs.entities.BooleanUtil.isNotNull;
import static org.hibernate.bugs.entities.BooleanUtil.not;

import jakarta.persistence.EntityManager;
import java.util.Collection;

public abstract class BaseEntity<T> {

    public abstract T getId();

    public <B> void jpaDeleteAndVerify(BaseEntity<B> baseEntity, EntityManager entityManager) {
        if (entityManager.contains(baseEntity)) {
            entityManager.remove(baseEntity);
        } else {
            @SuppressWarnings("unchecked") var ee = (T) entityManager.getReference(baseEntity.getClass(), baseEntity.getId());
            entityManager.remove(ee);
        }
        entityManager.flush();
        @SuppressWarnings("unchecked") var baseEntityDb = (B) entityManager.find(baseEntity.getClass(), baseEntity.getId());
        if (isNotNull(baseEntityDb)) {
            throw new RuntimeException("BaseEntity was not deleted '" + baseEntity.getId() + "'");
        }
    }

    public <B> void jpaDelete(BaseEntity<B> baseEntity, EntityManager entityManager) {
        entityManager.remove(baseEntity);
    }

    public <B extends BaseEntity<?>> void removeFromCollectionAndValidate(Collection<B> collection, B baseEntity) {
        var removedSuccessfully = collection.remove(baseEntity);
        if (not(removedSuccessfully)) {
            throw new RuntimeException("entity was not in set. key '" + baseEntity.getId() + "'");
        }
    }

    public void assertIdsAreEqual(SingleStringValueHolderId left, SingleStringValueHolderId right) {
        if (not(left.getValue().equals(right.getValue()))) {
            throw new RuntimeException("ids are not equal. left '" + left.getValue() + "' right '" + right.getValue() + "'");
        }
    }

    @Override
    public String toString() {
        return this.getId().toString();
    }

}
