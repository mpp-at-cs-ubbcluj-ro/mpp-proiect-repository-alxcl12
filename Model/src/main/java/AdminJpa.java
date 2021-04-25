/*
 *  @author albua
 *  created on 25/04/2021
 */

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Entity
@Table(name = "Admins")
public class AdminJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private byte[] passwordHash;
    private String passwordString;

    public AdminJpa(String username, byte[] passwordHash) {
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
                ", ID=" + id +
                '}';
    }
}
