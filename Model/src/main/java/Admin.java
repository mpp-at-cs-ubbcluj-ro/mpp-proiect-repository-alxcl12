/*
 *  @author albua
 *  created on 28/02/2021
 */

/**
 * Class used to model an administrator to the application
 */
public class Admin  extends Entity<Long>{
    private String username;
    private byte[] passwordHash;
    private String passwordString;

    public Admin(String username, byte[] passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordString() {
        return passwordString;
    }

    public void setPasswordString(String passwordString) {
        this.passwordString = passwordString;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", ID=" + ID +
                '}';
    }
}
