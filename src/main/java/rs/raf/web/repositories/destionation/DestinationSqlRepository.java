package rs.raf.web.repositories.destionation;

import rs.raf.web.entities.Destination;
import rs.raf.web.repositories.MySqlAbstractRepository;
import rs.raf.web.requests.DestinationEdit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DestinationSqlRepository extends MySqlAbstractRepository implements DestinationRepository{
    @Override
    public Destination findDestination(String name) {
        Destination destination = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM destinations where name = ?");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String description = resultSet.getString("description");
                destination = new Destination(name,description);
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

        return destination;
    }

    @Override
    public List<Destination> findAll() {
        List<Destination> destinations = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from destinations");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                destinations.add(new Destination(name,description));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return destinations;
    }

    @Override
    public List<Destination> destinationPerPage(int page) {
        List<Destination> destinations = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            statement = connection.prepareStatement(
                    "select * from destinations order by name limit 10 offset ?");
            statement.setInt(1,((page - 1) * 10));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                destinations.add(new Destination(name,description));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return destinations;
    }

    @Override
    public boolean edit(DestinationEdit destinationEdit) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE destinations SET name = ?, description = ? WHERE name = ?");
            preparedStatement.setString(1,destinationEdit.getNewName());
            preparedStatement.setString(2, destinationEdit.getDescription());
            preparedStatement.setString(3, destinationEdit.getOldName());
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
    public boolean delete(String destinationId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "delete from destinations WHERE name = ?");
            preparedStatement.setString(1, destinationId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            if(e.getSQLState().equals("23000") && e.getErrorCode() == 1451)
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
    public void addDestination(Destination destination) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO destinations (name, description) VALUES(?, ?)");
            preparedStatement.setString(1, destination.getName());
            preparedStatement.setString(2, destination.getDescription());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
    }
}
