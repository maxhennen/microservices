package domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "auth_user")
@NamedQueries({
        @NamedQuery(name = "AuthUser.login", query = "SELECT a FROM AuthUser a where a.email = :email and a.password = :password")
})
public class AuthUser implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    public AuthUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthUser(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
