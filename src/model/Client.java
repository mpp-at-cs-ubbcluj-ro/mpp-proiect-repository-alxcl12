/*
 *  @author albua
 *  created on 28/02/2021
 */

package model;

/**
 * Class used to model a client
 */
public class Client extends Entity<Long> {
    String firstName;
    String lastName;

    public Client(Long Id, String firstName, String lastName) {
        this.ID = Id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
