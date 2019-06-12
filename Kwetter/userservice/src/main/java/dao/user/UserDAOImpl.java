package dao.user;

import domain.Follower;
import domain.Following;
import domain.Group;
import domain.User;

import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Default
public class UserDAOImpl implements UserDAO{

    @PersistenceContext(unitName = "UserPersistenceUnit")
    private EntityManager em;

    List<User> users = new ArrayList<>();

    @Override
    public User createUser(User u) {
        users.add(u);

        return u;
    }

    @Override
    public User editUser(User u) {
        users.removeIf(user -> u.getId() == u.getId());
        users.add(u);
        return u;
    }

    @Override
    public boolean removeUser(User u) {
        users.removeIf(user -> u.getId() == u.getId());
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User findUserByEmail(String email) {
        for(User u : users){
            if(u.getEmail().equals(email))return u;
        }
        return null;
    }

    @Override
    public List<User> getAllFollowing(User u) {
        for(User us : users){
            if(us.getEmail().equals(u.getEmail())) return us.getFollowings();
        }
        return null;
    }

    @Override
    public List<User> getAllFollowers(User u) {
        for(User us : users){
            if(us.getEmail().equals(u.getEmail())) return us.getFollowers();
        }
        return null;
    }

    @Override
    public Follower addFollower(String emailFollower, String emailFollowing) {
        Follower follower = new Follower(emailFollowing, emailFollower);
        Following following = new Following(emailFollower, emailFollowing);

        em.persist(follower);
        em.persist(following);

        Follower follower1 = em.find(Follower.class, follower.getId());
        Following following1 = em.find(Following.class, following.getId());

        em.persist(follower1);
        em.persist(following1);

        return follower;
    }

    @Override
    public boolean removeFollower(Follower follower, Following following) {
        return removeFollowingFollower(following, follower);
    }

    @Override
    public boolean removeFollowing(Following following, Follower follower) {
        return removeFollowingFollower(following, follower);
    }

    private boolean removeFollowingFollower(Following following, Follower follower){

        em.remove(follower);
        em.remove(following);

        Following following1 = em.find(Following.class, following);
        Follower follower1 = em.find(Follower.class, follower);

        if(follower1 == null && following1 == null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Follower getFollowerByEmail(String email) {
        return (Follower)em.createNamedQuery("User.getFollowerByEmail")
                .setParameter("email", email);
    }

    @Override
    public Following getFollowingByEmail(String email) {
        return (Following) em.createNamedQuery("User.getFollowingByEmail")
                .setParameter("email", email);
    }

    @PreDestroy
    public void destroy(){
        em.close();
    }

}
