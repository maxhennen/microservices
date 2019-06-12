package dao.like;

import domain.Like;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Default
public class LikeDAOImpl implements LikeDAO {

    @PersistenceContext(unitName = "KweetPersistenceUnit")
    private EntityManager em;

    List<Like> likes = new ArrayList<>();

    @Override
    public Like create(Like l) {
        likes.add(l);
        return l;
    }

    @Override
    public Like removeLike(Like l) {
        likes.removeIf(like -> like.getId() == l.getId());
        return l;
    }

    @Override
    public List<Like> findAll() {
        return likes;
    }

    @Override
    public Like get(long id) {
        for (Like l : likes){
            if(l.getId() == id) return l;
        }
        return null;
    }
}
