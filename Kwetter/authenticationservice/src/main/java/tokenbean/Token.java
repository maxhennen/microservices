package tokenbean;

import domain.AuthUser;

public interface Token {
    String createToken(AuthUser user);
}
