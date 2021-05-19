package starter;/*
 *  @author albua
 *  created on 15/05/2021
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import repo.RepositoryTrip;

@ComponentScan("models")
@ComponentScan("repo")
@Import(value = {RepositoryTrip.class, TripController.class})
@SpringBootApplication
public class StartController {
    public static void main(String[] args) {
        SpringApplication.run(StartController.class, args);
    }
}
