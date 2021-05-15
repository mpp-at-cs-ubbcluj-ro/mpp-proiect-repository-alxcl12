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

import java.util.ArrayList;
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
            System.err.println("Exceptie " + e);
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
                result = session.createQuery("FROM AdminJpa WHERE username = :id", AdminJpa.class).setString("id", username) .list();
                transaction.commit();
            }
            catch (RuntimeException exception) {
                if (transaction != null) transaction.rollback();
            }
        }
        if(result == null || result.size() == 0){
            return null;
        }
        else {
            Admin ad = new Admin(result.get(0).getUsername(), result.get(0).getPasswordHash());
            ad.setID(result.get(0).getId());

            if(Arrays.equals(ad.getPasswordHash(), digest)){
                return ad;
            }
            return null;
        }
    }

    @Override
    public Admin findOne(Long aLong) {
        List<AdminJpa> result = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                result = session.createQuery("FROM AdminJpa WHERE ID = :id", AdminJpa.class).setLong("id", aLong).list();
                transaction.commit();
            }
            catch (RuntimeException exception) {
                if (transaction != null) transaction.rollback();
            }
        }

        if(result.size() != 0){
            AdminJpa adJpa = result.get(0);
            Admin toReturn = new Admin(adJpa.getUsername(), adJpa.getPasswordHash());
            toReturn.setID(adJpa.getId());
            toReturn.setPasswordString(adJpa.getPasswordString());
            return toReturn;
        }
        return null;
    }

    @Override
    public Iterable<Admin> findAll() {
        List<AdminJpa> result = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                result = session.createQuery("FROM AdminJpa", AdminJpa.class).list();
                transaction.commit();
            }
            catch (RuntimeException exception) {
                exception.printStackTrace();
                if (transaction != null) transaction.rollback();
            }
        }
        List<Admin> res = new ArrayList<>();
        result.forEach(x->{
            Admin ad = new Admin(x.getUsername(), x.getPasswordHash());
            ad.setID(x.getId());
            res.add(ad);
        });

        return res;
    }

    @Override
    public void save(Admin entity) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                AdminJpa adminJpa = new AdminJpa();
                adminJpa.setId(entity.getID());
                adminJpa.setPasswordHash(entity.getPasswordHash());
                adminJpa.setUsername(entity.getUsername());
                adminJpa.setPasswordString(entity.getPasswordString());

                session.save(adminJpa);
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

                List<AdminJpa> admin = session.createQuery("From AdminJpa where ID= :id", AdminJpa.class).setLong("id", aLong).list();

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
