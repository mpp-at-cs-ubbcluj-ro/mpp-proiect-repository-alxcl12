/*
 *  @author albua
 *  created on 11/03/2021
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtil {
    private Properties jdbcProps;

    private static final Logger logger= LogManager.getLogger();

    public JdbcUtil(){

    }

    private Connection instance = null;

    private Connection getNewConnection(){
        logger.traceEntry();

        String url = "jdbc:sqlite:C:\\\\Facultate\\\\New\\\\AnulII\\\\SemestrulIV\\\\MPP\\\\Laboratoare\\\\Database\\\\problem4db";
        logger.info("trying to connect to database ... {}",url);
        logger.info("user: ");
        logger.info("pass: ");
        Connection con=null;

        try {


                con = DriverManager.getConnection(url);

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error getting connection " + e);
        }
        return con;
    }

    public Connection getConnection(){
        logger.traceEntry();
        try {
            if (instance==null || instance.isClosed()) {
                instance = getNewConnection();
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(instance);
        return instance;
    }
}
