package tokenbean;


import domain.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.ejb.Stateless;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Stateless
public class JWToken implements Token {



    public String createToken(AuthUser user) {

        try {
            Date expired = Date.from(ZonedDateTime.now().plusHours(1).toInstant());
            Date issuedAt = Date.from(ZonedDateTime.now().toInstant());

            return Jwts.builder()
                    .setIssuer(user.getEmail())
                    .setExpiration(expired)
                    .setIssuedAt(issuedAt)
                    .signWith(SignatureAlgorithm.RS512, (PrivateKey)getRSAKeys().get("private"))
                    .setId(UUID.randomUUID().toString()).compact();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get RSA keys. Uses key size of 2048.
    private static Map<String, Object> getRSAKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        Map<String, Object> keys = new HashMap<String, Object>();
        keys.put("private", privateKey);
        keys.put("public", publicKey);
        return keys;
    }
}
