package utils;


import dao.group.GroupDAO;
import dao.user.UserDAO;
import domain.Details;
import domain.Group;
import domain.Location;
import domain.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class JPALoader {

    @Inject
    private UserDAO userDAO;
    @Inject
    private GroupDAO groupDAO;

    @PostConstruct
    public void init(){
        try {

            User user1 = new User("test1@test.nl", "Test1");
            User user2 = new User("test2@test.nl", "Test2");
            User user3 = new User("test3@test.nl", "Test3");

            Location location = new Location("NL","EHV", "Schoolstraat", "1");
            Location location1 = new Location("NL","EHV", "Schoolstraat", "1");
            Location location2= new Location("NL","EHV", "Schoolstraat", "1");

            Details details = new Details("hoi", "hoi.nl", location);
            Details details1 = new Details("hoi", "hoi.nl", location1);
            Details details2 = new Details("hoi", "hoi.nl", location2);

            user1.setDetails(details);
            user2.setDetails(details1);
            user3.setDetails(details2);

            userDAO.createUser(user1);
            userDAO.createUser(user2);
            userDAO.createUser(user3);

            groupDAO.create(new Group(user1.getEmail(), "ROLE_USER"));
            groupDAO.create(new Group(user2.getEmail(), "ROLE_ADMIN"));
            groupDAO.create(new Group(user3.getEmail(), "ROLE_MOD"));

            userDAO.addFollower(user1.getEmail(), user2.getEmail());
            userDAO.addFollower(user1.getEmail(), user3.getEmail());
            userDAO.addFollower(user2.getEmail(), user1.getEmail());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
