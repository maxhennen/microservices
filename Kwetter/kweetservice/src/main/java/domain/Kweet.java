package domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kweet")
@NamedQueries({
        @NamedQuery(name = "Kweet.findAll", query = "SELECT K from Kweet K"),
        @NamedQuery(name = "Kweet.getByID", query = "SELECT K FROM Kweet K where k.id = :id"),
        @NamedQuery(name = "Kweet.getKweetsByEmail", query = "SELECT K FROM Kweet K where K.emailUser = :email"),
//        @NamedQuery(name = "Kweet.getKweetsFromFollowing", query = "SELECT K FROM Kweet K inner join Following f on f.emailFollowing = k.emailUser " +
//                "inner join User u on f.emailUser = u.email where u.email = :email")
})
public class Kweet implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Column(name = "content")
    private String content;
    private List<Like> likes;
    @Column(name = "emailUser")
    private String emailUser;

    private List<Link> links = new ArrayList<>();

    public Kweet(LocalDateTime dateTime, String content, String emailUser) {
        this.dateTime= dateTime;
        this.content = content;
        this.emailUser = emailUser;
        likes = new ArrayList<>();
    }

    public Kweet() {
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getContent() {
        return content;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public String getUser() {
        return emailUser;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        dateTime = dateTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public void setUser(String user) {
        this.emailUser = user;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(String url, String rel){
        Link link = new Link(url, rel);
        links.add(link);
    }

    @Override
    public String toString() {
        return "Kweet{" +
                "id=" + id +
                ", DateTime=" + dateTime +
                ", content='" + content + '\'' +
                '}';
    }
}
