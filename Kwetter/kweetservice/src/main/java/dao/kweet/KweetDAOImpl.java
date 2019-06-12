package dao.kweet;


import domain.Kweet;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


@Stateless
@Default
public class KweetDAOImpl implements KweetDAO {


    @PersistenceContext(unitName = "KweetPersistenceUnit")
    private EntityManager em;

    private List<Kweet> kweets = new ArrayList<>();

    @Override
    public Kweet create(Kweet k) {
        k.setId(kweets.size()+1);
        kweets.add(k);
        return k;
    }

    @Override
    public Kweet edit(Kweet k) {
        kweets.removeIf(kweet -> kweet.getId() == k.getId());
        kweets.add(k);
        return k;
    }

    @Override
    public boolean removeKweet(Kweet k) {
        try {
            kweets.removeIf(kweet -> kweet.getId() == k.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Kweet> findAll() {
        return kweets;
    }

    @Override
    public Kweet get(long id) {
        for(Kweet k : kweets){
            if(k.getId() == id) return k;
        }
        return null;
    }

    @Override
    public List<Kweet> getKweetsByEmail(String email) {
        List<Kweet> kweetsUser = new ArrayList<>();
        for(Kweet k : kweets){
            if(k.getEmailUser().equals(email)) kweetsUser.add(k);
        }
        return kweetsUser;
    }

}
