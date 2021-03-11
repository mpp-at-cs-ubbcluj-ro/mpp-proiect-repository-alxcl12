/*
 *  @author albua
 *  created on 28/02/2021
 */
package model;

/**
 * Class used to model an administrator to the application
 */
public class Admin  extends Entity<Long>{
    private String username;
    private String passwordHash;

    public Admin(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", ID=" + ID +
                '}';
    }
}
