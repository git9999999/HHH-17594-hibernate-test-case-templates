package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Set;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.bugs.entities.address.AddressId;
import org.hibernate.bugs.entities.address.MyAddress;
import org.hibernate.bugs.entities.message.MyMessage;
import org.hibernate.bugs.entities.relation.AddressUserRelation;
import org.hibernate.bugs.entities.relation.AddressUserRelationId;
import org.hibernate.bugs.entities.user.MyUser;
import org.hibernate.bugs.entities.user.UserId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a standalone test case for Hibernate ORM.  Although this is perfectly acceptable as a reproducer, usage of
 * ORMUnitTestCase is preferred!
 */
public class ORMStandaloneTestCase {

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    private SessionFactory sf;

    @Before
    public void setup() {
        StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder()
            // Add in any settings that are specific to your test. See resources/hibernate.properties for the defaults.
            .applySetting("hibernate.show_sql", "true")
            .applySetting("hibernate.format_sql", "true")
            .applySetting("hibernate.hbm2ddl.auto", "update");

        Metadata metadata = new MetadataSources(srb.build())
            // Add your entities here.
            //	.addAnnotatedClass( Foo.class )
            .buildMetadata();

        sf = metadata.buildSessionFactory();
    }

    // Add your tests, using standard JUnit.

    @Test
    public void HHH_17594Test() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        MyAddress myAddress = setupData();

        entityManager.getTransaction().begin();

        System.out.println("find all AddressUserRelation in DB - start");
        Query fromAddressUserRelation1 = entityManager.createQuery("from AddressUserRelation");
        List<AddressUserRelation> resultList1 = fromAddressUserRelation1.getResultList();
        System.out.println("find all AddressUserRelation in DB - result list size " + resultList1.size());
        resultList1.forEach(
            addressUserRelation1 -> System.out.println("find all AddressUserRelation in DB - content " + addressUserRelation1.getId().getMyAddress().getId()));
        resultList1.forEach(
            addressUserRelation1 -> System.out.println("find all AddressUserRelation in DB - content " + addressUserRelation1.getId().getMyUser().getId()));
        System.out.println("find all AddressUserRelation in DB - end");

        MyAddress myAddressFromDB = entityManager.find(MyAddress.class, myAddress.getAddressId());
        System.out.println("Address from DB - id " + myAddressFromDB.getAddressId().getValue());
        System.out.println("Address from DB - getAddressUserRelations " + myAddressFromDB.getAddressUserRelations().size());

        AddressUserRelation addressUserRelation1 = findAddressUserRelation(entityManager);
        AddressUserRelationId id = addressUserRelation1.getId();
        System.out.println(id.getMyAddress().getAddressId());
        System.out.println(id.getMyUser().getId());

        entityManager.flush();
        entityManager.getTransaction().commit();

        // Assert
        entityManager.getTransaction().begin();

        assertResult(myAddress);


    }

    private void assertResult(MyAddress myAddress) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        createMyMessage(myAddress);
        MyMessage myMessage = findMyMessage(entityManager);
        System.out.println("MyMessage addressId - " + myMessage.getReceiverAddress().castToMyAddress().getAddressId().getValue());
        System.out.println("MyMessage getAddressUserRelations - " + myMessage.getReceiverAddress().castToMyAddress().getAddressUserRelations().size());
        Set<AddressUserRelation> addressUserRelations = myMessage.getReceiverAddress().castToMyAddress().getAddressUserRelations();
        addressUserRelations.forEach(addressUserRelation -> System.out.println("MyMessage " + addressUserRelation.getId().getMyAddress()));
        myMessage.getReceiverAddress().castToMyAddress().getAddressUserRelations()
            .forEach(addressUserRelation2 -> System.out.println(addressUserRelation2.getId().getMyUser()));
        System.out.println("urban 2");

        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    private MyMessage findMyMessage(EntityManager entityManager) {
        Query fromAddressUserRelation = entityManager.createQuery(" select e from MyMessage e");
        List<MyMessage> resultList = fromAddressUserRelation.getResultList();
        MyMessage myMessage = resultList.get(0);
        return myMessage;
    }

    private void createMyMessage(MyAddress myAddress) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        MyMessage myMessage = new MyMessage(myAddress);
        entityManager.persist(myMessage);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    private static AddressUserRelation findAddressUserRelation(EntityManager entityManager) {
        Query fromAddressUserRelation = entityManager.createQuery(" select e from AddressUserRelation e");
        List<AddressUserRelation> resultList = fromAddressUserRelation.getResultList();
        AddressUserRelation addressUserRelation1 = resultList.get(0);
        return addressUserRelation1;
    }

    private MyAddress setupData() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        MyAddress myAddress = new MyAddress(new AddressId());
        System.out.println("created address with id " + myAddress.getId().getValue());
        entityManager.persist(myAddress);
        entityManager.flush();

        MyUser myUser = new MyUser(new UserId());
        System.out.println("created user with id " + myUser.getId().getValue());
        entityManager.persist(myUser);
        entityManager.flush();

        AddressUserRelationId addressUserRelationId = new AddressUserRelationId(myUser, myAddress);
        AddressUserRelation addressUserRelation = new AddressUserRelation(addressUserRelationId);
        entityManager.persist(addressUserRelation);
        entityManager.flush();

        entityManager.flush();
        entityManager.getTransaction().commit();
        return myAddress;
    }

}
