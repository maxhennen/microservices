package domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "kweetLike")
@NamedQueries({
        @NamedQuery(name = "Like.findAll", query = "SELECT L FROM Like L"),
        @NamedQuery(name = "Like.getByID", query = "SELECT L FROM Like L WHERE L.id = :id")
})
public class Like implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    private String userEmail;
    private long kweetId;
    public Like(LocalDateTime dateTime, String userEmail, long kweetId) {
        this.dateTime = dateTime;
        this.userEmail = userEmail;
        this.kweetId = kweetId;
    }

    public Like() {
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getKweetId() {
        return kweetId;
    }

    public void setKweetId(long kweetId) {
        this.kweetId = kweetId;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", user=" + userEmail +
                '}';
    }
}
