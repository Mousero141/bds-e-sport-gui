package esport.but.feec.esport.data;

import esport.but.feec.esport.api.*;
import esport.but.feec.esport.config.DataSourceConfig;
import esport.but.feec.esport.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRepository {public AdminAuthView findPersonByEmail(String email) {
    try (Connection connection = DataSourceConfig.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
                 "SELECT email, pwd" +
                         " FROM admin a" +
                         " WHERE a.email = ?")
    ) {
        preparedStatement.setString(1, email);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return mapToPersonAuth(resultSet);
            }
        }
    } catch (SQLException e) {
        throw new DataAccessException("Find admin by ID with addresses failed.", e);
    }
    return null;
}

    public AdminDetailView findAdminDetailedView(Long personId) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT admin_id, nickname, given, surname, nickname, city, house_number, street" +
                             " FROM bds.person p" +
                             " LEFT JOIN bds.address a ON p.id_address = a.id_address" +
                             " WHERE p.id_person = ?")
        ) {
            preparedStatement.setLong(1, personId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToAdminDetailView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find person by ID with addresses failed.", e);
        }
        return null;
    }

    /**
     * What will happen if we do not use LEFT JOIN? What persons will be returned? Ask your self and repeat JOIN from the presentations
     *
     * @return list of persons
     */
    public List<AdminBasicView> getPersonsBasicView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id_person, email, first_name, surname, nickname, city" +
                             " FROM bds.person p" +
                             " LEFT JOIN bds.address a ON p.id_address = a.id_address");
             ResultSet resultSet = preparedStatement.executeQuery();) {
            List<AdminBasicView> personBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                personBasicViews.add(mapToPersonBasicView(resultSet));
            }
            return personBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Persons basic view could not be loaded.", e);
        }
    }

    public void createPerson(AdminCreateView AdminCreateView) {
        String insertPersonSQL = "INSERT INTO bds.person (email, first_name, nickname, pwd, surname) VALUES (?,?,?,?,?)";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, AdminCreateView.getEmail());
            preparedStatement.setString(2, AdminCreateView.getFirstName());
            preparedStatement.setString(3, AdminCreateView.getNickname());
            preparedStatement.setString(4, String.valueOf(AdminCreateView.getPwd()));
            preparedStatement.setString(5, AdminCreateView.getSurname());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating person failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating person failed operation on the database failed.");
        }
    }

    public void editPerson(AdminEditView personEditView) {
        String insertPersonSQL = "UPDATE admin a SET email = ?, first_name = ?, nickname = ?, surname = ? WHERE a.admin_id = ?";
        String checkIfExists = "SELECT email FROM admin a WHERE a.admin_id = ?";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, AdminEditView.getEmail());
            preparedStatement.setString(2, AdminEditView.getFirstName());
            preparedStatement.setString(3, AdminEditView.getNickname());
            preparedStatement.setString(4, AdminEditView.getSurname());
            preparedStatement.setLong(5, AdminEditView.getId());

            try {
                // TODO set connection autocommit to false
                /* HERE */
                try (PreparedStatement ps = connection.prepareStatement(checkIfExists, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, personEditView.getId());
                    ps.execute();
                } catch (SQLException e) {
                    throw new DataAccessException("This person for edit do not exists.");
                }

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new DataAccessException("Creating person failed, no rows affected.");
                }
                // TODO commit the transaction (both queries were performed)
                /* HERE */
            } catch (SQLException e) {
                // TODO rollback the transaction if something wrong occurs
                /* HERE */
            } finally {
                // TODO set connection autocommit back to true
                /* HERE */
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating person failed operation on the database failed.");
        }
    }


    /**
     * <p>
     * Note: In practice reflection or other mapping frameworks can be used (e.g., MapStruct)
     * </p>
     */
    private AdminAuthView mapToPersonAuth(ResultSet rs) throws SQLException {
        AdminAuthView person = new AdminAuthView();
        person.setEmail(rs.getString("email"));
        person.setPassword(rs.getString("pwd"));
        return person;
    }

    private AdminBasicView mapToPersonBasicView(ResultSet rs) throws SQLException {
        AdminBasicView personBasicView = new AdminBasicView();
        AdminBasicView.setAdminId(rs.getLong("id_person"));
        AdminBasicView.setEmail(rs.getString("email"));
        AdminBasicView.setGivenName(rs.getString("first_name"));
        AdminBasicView.setFamilyName(rs.getString("surname"));
        AdminBasicView.setNickname(rs.getString("nickname"));
        AdminBasicView.setCity(rs.getString("city"));
        return personBasicView;
    }

    private AdminDetailView mapToAdminDetailView(ResultSet rs) throws SQLException {
        PersonDetailView personDetailView = new PersonDetailView();
        personDetailView.setId(rs.getLong("id_person"));
        personDetailView.setEmail(rs.getString("email"));
        personDetailView.setGivenName(rs.getString("first_name"));
        personDetailView.setFamilyName(rs.getString("surname"));
        personDetailView.setNickname(rs.getString("nickname"));
        personDetailView.setCity(rs.getString("city"));
        personDetailView.sethouseNumber(rs.getString("house_number"));
        personDetailView.setStreet(rs.getString("street"));
        return personDetailView;
    }
}
