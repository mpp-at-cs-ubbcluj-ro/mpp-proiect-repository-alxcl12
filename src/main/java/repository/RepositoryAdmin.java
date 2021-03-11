/*
 *  @author albua
 *  created on 28/02/2021
 */
package repository;
import model.Admin;
import model.validators.AdminValidator;
import model.validators.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;

/**
 * Repository used to store admins
 */
public class RepositoryAdmin implements AdminRepoInterface{
    AdminValidator validator;

    private JdbcUtil dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public RepositoryAdmin(Properties props, AdminValidator validator){
        logger.info("Initializing AdminRepository with properties: {} ", props);
        dbUtils = new JdbcUtil(props);
        this.validator = validator;
    }

    @Override
    public Admin findOne(Long aLong) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        Admin admin = null;
        try (PreparedStatement preStmt = con.prepareStatement("select * from Admins where ID=?")){
            preStmt.setLong(1, aLong);
            try (ResultSet result = preStmt.executeQuery()){
                if (result.next()){
                    Long id = result.getLong("ID");
                    String username = result.getString("username");
                    String passwordHash = result.getString("passwordHash");

                    admin = new Admin(username, passwordHash);
                    admin.setID(id);
                }
            }
        }
        catch (SQLException e){
            logger.error(e);
        }
        logger.traceExit();
        return admin;
    }

    @Override
    public Iterable<Admin> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        HashSet<Admin> admins = new HashSet<>();

        try (PreparedStatement preStmt = con.prepareStatement("select * from Admins")){
            try (ResultSet result = preStmt.executeQuery()){
                while (result.next()){
                    Long id = result.getLong("ID");
                    String username = result.getString("username");
                    String passwordHash = result.getString("passwordHash");

                    Admin admin = new Admin(username, passwordHash);
                    admin.setID(id);
                    admins.add(admin);
                }
            }
        }
        catch (SQLException e){
            logger.error(e);
        }
        logger.traceExit();

        return admins;
    }

    @Override
    public void save(Admin entity) {
        logger.traceEntry("saving admin {}", entity);
        Connection con = dbUtils.getConnection();

        try {
            validator.validate(entity);
        }
        catch (ValidationException e){
            logger.error(e);
            return;
        }

        try(PreparedStatement preStmt = con.prepareStatement("insert into Admins (username, passwordHash) values (?,?)")){

            preStmt.setString(1, entity.getUsername());
            preStmt.setString(2, entity.getPasswordHash());

            int result = preStmt.executeUpdate();

            logger.trace("saved {} instances", result);
        }
        catch (SQLException e){
            logger.error(e);
        }

        logger.traceExit();
    }

    @Override
    public void delete(Long aLong) {
        logger.traceEntry("deleting admin with id {}", aLong);
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("delete from Admins where ID=?")){
            preStmt.setLong(1, aLong);

            int result = preStmt.executeUpdate();
            logger.trace("deleted {} instances with ID", result);
        }
        catch (SQLException e){
            logger.error(e);
        }

        logger.traceExit();
    }

    @Override
    public void update(Admin entity) {
        logger.traceEntry("updating admin with id {}", entity.getID());
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("update Admins set username = ?, passwordHash = ? where" +
                " ID=?")){

            preStmt.setString(1, entity.getUsername());
            preStmt.setString(2, entity.getPasswordHash());
            preStmt.setLong(3, entity.getID());

            int result = preStmt.executeUpdate();
            logger.trace("updated {} instances", result);
        }
        catch (SQLException e){
            logger.error(e);
        }

        logger.traceExit();
    }
}
