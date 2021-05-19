/*
 *  @author albua
 *  created on 15/05/2021
 */
package starter;

import models.Trip;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

public class StartClient {
    private final static TripClient tripsClient =new TripClient();
    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
        Trip trip=new Trip("testSource6", "testDest6", LocalDateTime.now(), 18);
        try{

            show(()-> System.out.println(tripsClient.create(trip)));
            show(()->{
                Trip[] res= tripsClient.getAll();
                for(Trip t:res){
                    System.out.println(t.getID()+": "+t.getDestination());
                }
            });
        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }

    }



    private static void show(Runnable task) {
        try {
            task.run();
        } catch (RuntimeException e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }
}
