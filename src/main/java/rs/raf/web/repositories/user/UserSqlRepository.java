package rs.raf.web.repositories.user;

import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.web.entities.User;
import rs.raf.web.entities.enums.Status;
import rs.raf.web.entities.enums.UserType;
import rs.raf.web.repositories.MySqlAbstractRepository;
import rs.raf.web.requests.EditInfoUser;
import rs.raf.web.requests.UserEdit;
import rs.raf.web.requests.UserNew;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserSqlRepository extends MySqlAbstractRepository implements UserRepository {
    @Override
    public User findUser(String email) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String password = resultSet.getString("password");
                String status = resultSet.getString("status");
                String userType = resultSet.getString("type");
                user = new User(email,password);
                if(userType.equals("ADMIN")){
                    user.setStatus(Status.ACTIVATED);
                    user.setUserType(UserType.ADMIN);
                }else {
                    user.setUserType(UserType.EDITOR);
                    if(status.equals("ACTIVATED"))
                        user.setStatus(Status.ACTIVATED);
                    else
                        user.setStatus(Status.DEACTIVATED);
                }
                System.out.println(user);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }
    public int getMeUser(String email){
        int pass = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                pass = resultSet.getInt("userId");
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return pass;
    }

    @Override
    public List<User> getPerPage(int page) {
        List<User> users = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            statement = connection.prepareStatement(
                    "select * from users order by userId limit 10 offset ?");
            statement.setInt(1,((page - 1) * 10));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("userId");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                User user = new User(id,firstName,lastName);
                String email = resultSet.getString("email");
                user.setEmail(email);
                String status = resultSet.getString("status");
                String userType = resultSet.getString("type");
                if(userType.equals("ADMIN")){
                    user.setStatus(Status.ACTIVATED);
                    user.setUserType(UserType.ADMIN);
                }else {
                    user.setUserType(UserType.EDITOR);
                    if(status.equals("ACTIVATED"))
                        user.setStatus(Status.ACTIVATED);
                    else
                        user.setStatus(Status.DEACTIVATED);
                }
                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return users;
    }

    @Override
    public boolean addUser(UserNew user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"userId"};
            String hashedPassword = DigestUtils.sha256Hex(user.getPassword());

            preparedStatement = connection.prepareStatement("INSERT INTO users (firstName, lastName, status, type, password, email) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, "ACTIVATED");
            preparedStatement.setString(4, user.getUserType());
            preparedStatement.setString(5, hashedPassword);
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            if(e.getSQLState().equals("23000") && e.getErrorCode() == 1062)
                return false;
            else {
                e.printStackTrace();
            }
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return true;
    }

    @Override
    public void deleteUser(int userId) {

    }

    @Override
    public void editUser(UserEdit user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE users SET status = ? WHERE userId = ?");
            preparedStatement.setInt(2,user.getUserId());
            preparedStatement.setString(1, user.getStatus());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
    }
    @Override
    public void editInfoUser(EditInfoUser user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE users SET firstName = ?,lastName = ?, email = ? WHERE userId = ?");
            preparedStatement.setString(1,user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4,user.getUserId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
    }


}
