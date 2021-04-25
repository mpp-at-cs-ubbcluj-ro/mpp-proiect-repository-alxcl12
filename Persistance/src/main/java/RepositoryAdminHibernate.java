/*
 *  @author albua
 *  created on 24/04/2021
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import java.util.Arrays;
import java.util.List;

public class RepositoryAdminHibernate implements AdminRepoInterface{
    static SessionFactory sessionFactory;

    public RepositoryAdminHibernate(){
        initialize();
    }

    static void initialize(){
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exceptie "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    @Override
    public Admin authenticateAdmin(String username, String password) throws NoSuchAlgorithmException {
        List<AdminJpa> result = null;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte[] digest = md.digest();

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                var result22 = session.createQuery("FROM Admin ", AdminJpa.class).list();
                result = session.createQuery("FROM Admin WHERE username = :id", AdminJpa.class).setString("id", username) .list();
                transaction.commit();
            }
            catch (RuntimeException exception) {
                if (transaction != null) transaction.rollback();
            }
        }
        if(result.size() == 0){
            return null;
        }
        else {
//            Admin ad = result.get(0);
//            if(Arrays.equals(ad.getPasswordHash(), digest)){
//                return ad;
//            }
//            return null;
        }
        return null;
    }

    @Override
    public Admin findOne(Long aLong) {
        List<Admin> result = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                result = session.createQuery("FROM Admins WHERE ID = :id", Admin.class).setLong("id", aLong) .list();
                transaction.commit();
            }
            catch (RuntimeException exception) {
                if (transaction != null) transaction.rollback();
            }
        }

        return result.get(0);
    }

    @Override
    public Iterable<Admin> findAll() {
        List<Admin> result = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                result = session.createQuery("FROM Admins", Admin.class).list();
                transaction.commit();
            }
            catch (RuntimeException exception) {
                if (transaction != null) transaction.rollback();
            }
        }
        return result;
    }

    @Override
    public void save(Admin entity) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void delete(Long aLong) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                List<Admin> admin = session.createQuery("From Admins where ID= :id", Admin.class).setLong("id", aLong).list();

                if (admin.size()!=0){
                    session.delete(admin.get(0));
                    tx.commit();
                }

            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void update(Admin entity) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();

            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }
}
