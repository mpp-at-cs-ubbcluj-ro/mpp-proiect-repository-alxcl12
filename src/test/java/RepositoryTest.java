/*
 *  @author albua
 *  created on 11/03/2021
 */

import model.Admin;
import model.Client;
import model.validators.AdminValidator;
import model.validators.ClientValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.RepositoryAdmin;
import repository.RepositoryClient;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.stream.StreamSupport;

public class RepositoryTest {

    @Test
    public void adminRepoTest(){
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+ e);
        }

        AdminValidator validator = new AdminValidator();
        RepositoryAdmin adminRepo=new RepositoryAdmin(props, validator);
        Random random = new Random();

        int number = random.nextInt();

        Admin testAdmin = new Admin("TestAdminUsername" + number, "TestPasswordHash" + number);

        adminRepo.save(testAdmin);

        Iterable<Admin> admins = adminRepo.findAll();

        Assertions.assertNotEquals(0, StreamSupport.stream(admins.spliterator(), false).count());

        Admin recover = adminRepo.findOne(1L);

        Assertions.assertEquals("TestAdminUsername-919258746", recover.getUsername());
    }

    @Test
    public void clientRepoTest(){
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+ e);
        }

        ClientValidator validator = new ClientValidator();
        RepositoryClient clientRepo = new RepositoryClient(props, validator);
        Random random = new Random();

        int number = random.nextInt();

        Client testClient = new Client("TestClientFirstName" + number, "TestClientLastName" + number);

        clientRepo.save(testClient);

        Iterable<Client> clients = clientRepo.findAll();

        Assertions.assertNotEquals(0, StreamSupport.stream(clients.spliterator(), false).count());

        Client recover = clientRepo.findOne(1L);

        Assertions.assertEquals("TestClientFirstName-2045517797", recover.getFirstName());
    }
}
