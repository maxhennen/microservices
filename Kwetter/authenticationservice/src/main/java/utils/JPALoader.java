package utils;


import dao.AuthDao;
import domain.AuthUser;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;


@Singleton
@Startup
public class JPALoader {

    @Inject
    private AuthDao authDao;

    @PostConstruct
    public void init(){
        try {

            authDao.register("test1@test.nl", "Test123");
            authDao.register("test2@test.nl", "Test123");
            authDao.register("test3@test.nl", "Test123");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
