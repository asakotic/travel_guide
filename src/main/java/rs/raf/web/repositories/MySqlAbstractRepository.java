package rs.raf.web.repositories;

import java.sql.*;
import java.util.Optional;

public abstract class MySqlAbstractRepository {
    public MySqlAbstractRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Connection newConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://" + this.getHost() + ":" + this.getPort() + "/" + this.getDatabaseName(), this.getUsername(), this.getPassword()
        );
    }

    protected String getHost() {
        return "localhost";
    }

    protected int getPort() {
        return 3306;
    }

    protected String getDatabaseName() {
        return "tour";
    }

    protected String getUsername() {
        return "root";
    }

    protected String getPassword() {
        return "";
    }

    protected void closeStatement(Statement statement) {
        try {
            if(statement == null)
                return;
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void closeResultSet(ResultSet resultSet) {
        try {
            if(resultSet == null)
                return;
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void closeConnection(Connection connection) {
        try {
            if(connection == null)
                return;
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    protected void rollback(Connection connection) {
        try {
            if(connection == null)
                return;
            connection.rollback();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
