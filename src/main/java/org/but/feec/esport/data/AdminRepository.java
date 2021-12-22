package org.but.feec.esport.data;

import org.but.feec.esport.api.*;
import org.but.feec.esport.config.DataSourceConfig;
import org.but.feec.esport.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRepository {
    public AdminAuthView findAdminByEmail(String email)
    {
    try (Connection connection = DataSourceConfig.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
                 "SELECT email, pwd" +
                         " FROM admin a" +
                         " WHERE a.email = ?")
    ) {
        preparedStatement.setString(1, email);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return mapToAdminAuth(resultSet);
            }
        }
    } catch (SQLException e) {
        throw new DataAccessException("Find admin by ID with addresses failed.", e);
    }
    return null;
}



     // JAK ULOZIT DATE TIME ?? JAVA FX StringProperty? AdminDetailView
    public AdminDetailView findAdminDetailedView(Long adminId) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT admin_id, nickname, given, surname, nickname" +
                             " FROM bds.person p" +
                             " LEFT JOIN bds.address a ON p.id_address = a.id_address" +
                             " WHERE p.id_person = ?")
        ) {
            preparedStatement.setLong(1, adminId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    //return mapToAdminDetailView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find admin by ID with addresses failed.", e);
        }
        return null;
    }

    public List<AdminBasicView> getAdminsBasicView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT a.admin_id, a.given_name, a.nickname, a.given_name, a.email" +
                             " FROM admin a " +
                             " LEFT JOIN match m ON a.admin_id = m.admin_id");
             ResultSet resultSet = preparedStatement.executeQuery();) {
            List<AdminBasicView> adminBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                adminBasicViews.add(mapToAdminBasicView(resultSet));
            }
            return adminBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Admins basic view could not be loaded.", e);
        }
    }

    public void createAdmin(AdminCreateView adminCreateView) {
        String insertAdminSQL = "INSERT INTO admin (nickname, email, password, given_name, family_name) VALUES (?,?,?,?,?)";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertAdminSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, adminCreateView.getEmail());
            preparedStatement.setString(2, adminCreateView.getFirstName());
            preparedStatement.setString(3, adminCreateView.getNickname());
            preparedStatement.setString(4, String.valueOf(adminCreateView.getPwd()));
            preparedStatement.setString(5, adminCreateView.getSurname());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating admin failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating admin failed operation on the database failed.");
        }
    }

    public void editAdmin(AdminEditView adminEditView) {
        String insertPersonSQL = "UPDATE admin a SET email = ?, first_name = ?, nickname = ?, surname = ? WHERE a.admin_id = ?";
        String checkIfExists = "SELECT email FROM admin a WHERE a.admin_id = ?";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, adminEditView.getEmail());
            preparedStatement.setString(2, adminEditView.getFirstName());
            preparedStatement.setString(3, adminEditView.getNickname());
            preparedStatement.setString(4, adminEditView.getSurname());
            preparedStatement.setLong(5, adminEditView.getId());

            try {
                // TODO set connection autocommit to false
                connection.setAutoCommit(false);
                try (PreparedStatement ps = connection.prepareStatement(checkIfExists, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, adminEditView.getId());
                    ps.execute();
                } catch (SQLException e) {
                    throw new DataAccessException("This admin for edit do not exists.");
                }

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new DataAccessException("Creating person failed, no rows affected.");
                }
                // TODO commit the transaction (both queries were performed)
                connection.commit();
            } catch (SQLException e) {
                // TODO rollback the transaction if something wrong occurs
                connection.rollback();
            } finally {
                // TODO set connection autocommit back to true
                connection.setAutoCommit(true);
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
    private AdminAuthView mapToAdminAuth(ResultSet rs) throws SQLException {
        AdminAuthView person = new AdminAuthView();
        person.setEmail(rs.getString("email"));
        person.setPassword(rs.getString("pwd"));
        return person;
    }

    private AdminBasicView mapToAdminBasicView(ResultSet rs) throws SQLException {
        AdminBasicView adminBasicView = new AdminBasicView();
        adminBasicView.setAdminId(rs.getLong("admin_id"));
        adminBasicView.setEmail(rs.getString("email"));
        adminBasicView.setGivenName(rs.getString("first_name"));
        adminBasicView.setFamilyName(rs.getString("surname"));
        adminBasicView.setNickname(rs.getString("nickname"));
        return adminBasicView;
    }

    private AdminDetailView mapToAdminDetailView(ResultSet rs) throws SQLException {
        AdminDetailView adminDetailView = new AdminDetailView();
        adminDetailView.setId(rs.getLong("id_person"));
        adminDetailView.setEmail(rs.getString("email"));
        adminDetailView.setGivenName(rs.getString("first_name"));
        adminDetailView.setFamilyName(rs.getString("surname"));
        adminDetailView.setNickname(rs.getString("nickname"));
        return adminDetailView;
    }
}
