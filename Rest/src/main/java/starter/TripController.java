package starter;/*
 *  @author albua
 *  created on 15/05/2021
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repo.TripRepoInterface;
import models.Trip;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripRepoInterface tripRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Trip[] getAll(){
        var all = tripRepository.findAll();
        var list = StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());

        Trip[] trips = new Trip[list.size()];
        for(int i=0;i<list.size();i++){
            trips[i] = list.get(i);
        }
        return trips;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Long id){

        Trip trip=tripRepository.findOne(id);
        if (trip==null)
            return new ResponseEntity<String>("Trip not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Trip>(trip, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Trip create(@RequestBody Trip trip){
        tripRepository.save(trip);
        return trip;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Trip update(@RequestBody Trip trip) {
        System.out.println("Updating trip ...");
        tripRepository.update(trip);
        return trip;

    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        System.out.println("Deleting trip with id ... "+id);
        try {
            tripRepository.delete(id);
            return new ResponseEntity<Trip>(HttpStatus.OK);
        }catch (Exception ex){
            System.out.println("Ctrl Delete trip exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
