package dao;

import domain.AuthUser;
import domain.Token;

public interface AuthDao {

    AuthUser login(String email, String password);
    AuthUser register(String email, String password);
    Token addToken(Token token);
    boolean checkToken(String token);
}
