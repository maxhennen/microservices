package services;


import dao.kweet.KweetDAO;
import domain.Kweet;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class KweetService {

    @Inject
    private KweetDAO kweetDAO;

    /**
     * Used for testing only!
     *
     * @param dao
     */
    public void setKweetDAO(KweetDAO dao) {
        kweetDAO = dao;
    }


    /**
     * saves a kweet and add it to the user
     * @param kweet
     */
    public Kweet createKweet(Kweet kweet){
        return kweetDAO.create(kweet);
    }

    /**
     * Updates a kweet
     * @param kweet
     */
    public Kweet editKweet(Kweet kweet){
        return kweetDAO.edit(kweet);
    }

    /**
     * Removes kweet
     * @param kweet
     */
    public boolean removeKweet(Kweet kweet){
        return kweetDAO.removeKweet(kweet);
    }

    /**
     * retrieves all kweets
     * @return
     */
    public List<Kweet> getAllKweets(){
        return kweetDAO.findAll();
    }

    /**
     * Retrieves a kweet by id
     * @param id
     * @return
     */
    public Kweet getKweetById(long id){
        return kweetDAO.get(id);
    }

    /**
     * Retrieves all kweets from an email
     * @param email
     * @return
     */
    public List<Kweet> getKweetsByEmail(String email){
        return kweetDAO.getKweetsByEmail(email);
    }
}
