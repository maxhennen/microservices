package domain;


import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT U FROM User U"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT U FROM User u WHERE U.email = :email"),
        @NamedQuery(name = "User.getFollowers", query = "SELECT U From User U inner join Follower F on U.email = F.emailFollower where F.emailUser = :email"),
        @NamedQuery(name = "User.getFollowing", query = "SELECT U FROM User U inner join Following F on U.email = F.emailFollowing where F.emailUser = :email"),
        @NamedQuery(name = "User.getFollowerByEmail", query = "SELECT F FROM Follower F where F.emailFollower = :email"),
        @NamedQuery(name = "User.getFollowingByEmail", query = "SELECT F FROM Following F where F.emailFollowing = :email")
})
public class User implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Details details;

    @Transient
    @JsonInclude
    private List<Long> likes;
    @JsonInclude
    @Transient
    private List<Long> kweets;
    @JsonInclude
    @Transient
    private List<User> followings;
    @JsonInclude
    @Transient
    private List<User> followers;

    public User(long id, String name, String email, List<Group> groups, Details details) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.details = details;
    }

    public User(String email, String name) {
        this.name = name;
        this.email = email;
        details = new Details();
    }

    public User() {
        details = new Details();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public Details getDetails() {
        return details;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public List<Long> getLikes() {
        return likes;
    }

    public void setLikes(List<Long> likes) {
        this.likes = likes;
    }

    public List<Long> getKweets() {
        return kweets;
    }

    public void setKweets(List<Long> kweets) {
        this.kweets = kweets;
    }

    public List<User> getFollowings() {
        return followings;
    }

    public void setFollowings(List<User> followings) {
        this.followings = followings;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    //    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", details=" + details +
//                '}';
//    }

}
