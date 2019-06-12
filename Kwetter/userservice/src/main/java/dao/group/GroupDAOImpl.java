package dao.group;



import domain.Group;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Default
public class GroupDAOImpl implements GroupDAO {

    @PersistenceContext(unitName = "UserPersistenceUnit")
    private EntityManager em;

    List<Group> groups = new ArrayList<>();

    @Override
    public Group create(Group g) {
        groups.add(g);
        return g;
    }

    @Override
    public Group getByUserEmail(String email) {
        for(Group g : groups){
            if(g.getEmail().equals(email)) return g;
        }
        return null;
    }

    @Override
    public Group update(Group g) {
        groups.removeIf(group -> g.getEmail().equals(g.getEmail()));
        groups.add(g);
        return g;
    }

    @Override
    public List<Group> getAllGroups() {
        return groups;
    }
}
