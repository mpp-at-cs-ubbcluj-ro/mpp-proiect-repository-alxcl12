package model;/*
 *  @author albua
 *  created on 28/02/2021
 */

public class Admin  extends Entity<Long>{
    String username;
    String passwordHash;

    public Admin(Long Id, String username, String passwordHash) {
        this.ID = Id;
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
}
