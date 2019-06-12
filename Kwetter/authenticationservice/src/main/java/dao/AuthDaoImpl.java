package dao;

import domain.AuthUser;
import domain.Token;
import io.jsonwebtoken.ExpiredJwtException;
import utils.AuthenticationUtils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class AuthDaoImpl implements AuthDao {

    @PersistenceContext(unitName = "authPersistenceUnit")
    private EntityManager em;

    private List<AuthUser> users = new ArrayList<>();
    private List<Token> tokens = new ArrayList<>();

    @Override
    public AuthUser login(String email, String password) {
        for(AuthUser u : users){
            if(u.getEmail().equals(email) && u.getPassword().equals(password)) return u;
        }
        return null;
    }

    @Override
    public AuthUser register(String email, String password) {
        AuthUser authUser = new AuthUser(email, AuthenticationUtils.encodeSHA256(password));
        users.add(authUser);
        return null;
    }

    @Override
    public Token addToken(Token token) {
        tokens.add(token);
        return token;
    }

    @Override
    public boolean checkToken(String token) {
        for(Token t : tokens){
            if(t.getToken().equals(token) && t.getExpirationTime().isAfter(LocalDateTime.now())) return true;
        }
        return false;
    }
}
