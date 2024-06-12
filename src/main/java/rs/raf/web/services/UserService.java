package rs.raf.web.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.web.entities.User;
import rs.raf.web.entities.enums.Status;
import rs.raf.web.repositories.user.UserRepository;
import rs.raf.web.requests.EditInfoUser;
import rs.raf.web.requests.UserEdit;
import rs.raf.web.requests.UserNew;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class UserService {
    @Inject
    UserRepository userRepository;

    public String login(String email, String password) {
        String hashedPassword = DigestUtils.sha256Hex(password);

        User user = this.userRepository.findUser(email);
        if (user == null || !user.getPassword().equals(hashedPassword)) {
            return null;
        }

        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withClaim("email", email)
                .withClaim("type", user.getUserType().toString())
                .sign(algorithm);
    }

    public boolean isAuthorized(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getClaim("email").asString();

        User user = this.userRepository.findUser(email);
        System.out.println(user);

        if (user == null || user.getStatus() == Status.DEACTIVATED){
            System.out.println(user);
            return false;
        }

        return true;
    }
    public boolean isAdmin(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String type = jwt.getClaim("type").asString();

        return type.equals("ADMIN");

    }

    public List<User> getPerPage(int page) {
        return this.userRepository.getPerPage(page);
    }

    public void editUser(UserEdit userEdit){
        this.userRepository.editUser(userEdit);
    }

    public void editInfoUser(EditInfoUser editInfoUser) {
        this.userRepository.editInfoUser(editInfoUser);
    }

    public boolean addNew(UserNew user) {
        return this.userRepository.addUser(user);
    }
}
