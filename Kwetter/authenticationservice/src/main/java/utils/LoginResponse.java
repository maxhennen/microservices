package utils;

import domain.AuthUser;
import java.io.Serializable;

public class LoginResponse implements Serializable {

    private String token;
    private AuthUser user;
    private int expiresIn;

    public LoginResponse(String token, AuthUser user){
        this.token = token;
        this.user = user;
        this.expiresIn = 3600;
    }

    public LoginResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthUser getUser() {
        return user;
    }

    public void setUser(AuthUser user) {
        this.user = user;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

        @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}