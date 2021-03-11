/*
 *  @author albua
 *  created on 04/03/2021
 */
package repository;

import model.Client;
import model.validators.ClientValidator;
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
 * Repository used to store clients
 */
public class RepositoryClient implements ClientRepoInterface{
    ClientValidator validator;

    private JdbcUtil dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public RepositoryClient(Properties props, ClientValidator validator){
        logger.info("Initializing ClientRepository with properties: {} ", props);
        dbUtils = new JdbcUtil(props);
        this.validator = validator;
    }

    @Override
    public Client findOne(Long aLong) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        Client client = null;
        try (PreparedStatement preStmt = con.prepareStatement("select * from Clients where ID=?")){
            preStmt.setLong(1, aLong);
            try (ResultSet result = preStmt.executeQuery()){
                if (result.next()){
                    Long id = result.getLong("ID");
                    String firstName = result.getString("firstName");
                    String lastName = result.getString("lastName");

                    client = new Client(firstName, lastName);
                    client.setID(id);
                }
            }
        }
        catch (SQLException e){
            logger.error(e);
        }
        logger.traceExit();
        return client;
    }

    @Override
    public Iterable<Client> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        HashSet<Client> clients = new HashSet<>();

        try (PreparedStatement preStmt = con.prepareStatement("select * from Clients")){
            try (ResultSet result = preStmt.executeQuery()){
                while (result.next()){
                    Long id = result.getLong("ID");
                    String firstName = result.getString("firstName");
                    String lastName = result.getString("lastName");

                    Client client = new Client(firstName, lastName);
                    client.setID(id);
                    clients.add(client);
                }
            }
        }
        catch (SQLException e){
            logger.error(e);
        }
        logger.traceExit();

        return clients;
    }

    @Override
    public void save(Client entity) {
        logger.traceEntry("saving client {}", entity);
        Connection con = dbUtils.getConnection();

        try {
            validator.validate(entity);
        }
        catch (ValidationException e){
            logger.error(e);
            return;
        }

        try(PreparedStatement preStmt = con.prepareStatement("insert into Clients (firstName, lastName) values (?,?)")){

            preStmt.setString(1, entity.getFirstName());
            preStmt.setString(2, entity.getLastName());

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
        logger.traceEntry("deleting client with id {}", aLong);
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("delete from Clients where ID=?")){
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
    public void update(Client entity) {
        logger.traceEntry("updating client with id {}", entity.getID());
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("update Clients set firstName = ?, lastName = ? where" +
                " ID=?")){

            preStmt.setString(1, entity.getFirstName());
            preStmt.setString(2, entity.getLastName());
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
