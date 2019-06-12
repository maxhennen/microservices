package utils;


import dao.kweet.KweetDAO;
import domain.Kweet;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.time.LocalDateTime;


@Singleton
@Startup
public class JPALoader {

    @Inject
    private KweetDAO kweetDAO;

    @PostConstruct
    public void init(){
        try {

            for(int i = 0; i < 10; i ++){
                kweetDAO.create(new Kweet(LocalDateTime.now(), "Hoi " + i, "test1@test.nl"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
