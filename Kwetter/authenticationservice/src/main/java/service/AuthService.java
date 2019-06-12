package service;

import dao.AuthDao;
import domain.AuthUser;
import domain.Token;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AuthService {

    @Inject
    private AuthDao authDao;

    public AuthUser login(String email, String password){return authDao.login(email, password);}

    public AuthUser register(String email, String password){return authDao.register(email, password);}

    public Token addToken(Token token){return authDao.addToken(token);}

    public boolean checkToken(String token) {return authDao.checkToken(token);}
}
