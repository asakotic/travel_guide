package rs.raf.web.repositories.user;

import rs.raf.web.entities.User;
import rs.raf.web.entities.enums.UserType;
import rs.raf.web.requests.EditInfoUser;
import rs.raf.web.requests.UserEdit;
import rs.raf.web.requests.UserNew;

import java.util.List;

public interface UserRepository {
    User findUser(String username);
    boolean addUser(UserNew user);
    void deleteUser(int userId);
    void editUser(UserEdit user);
    int getMeUser(String email);

    List<User> getPerPage(int page);

    void editInfoUser(EditInfoUser user);
}


