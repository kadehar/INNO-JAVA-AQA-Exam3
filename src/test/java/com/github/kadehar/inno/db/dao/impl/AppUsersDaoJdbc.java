package com.github.kadehar.inno.db.dao.impl;

import com.github.kadehar.inno.db.dao.AppUsersDao;
import com.github.kadehar.inno.db.entity.AppUserEntity;

import java.sql.*;

public class AppUsersDaoJdbc implements AppUsersDao {

    private final Connection connection;

    public AppUsersDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public AppUserEntity create(AppUserEntity user) {
        try (PreparedStatement ps = connection.prepareStatement(
                "insert into app_users(is_active, login, \"password\", display_name, \"role\")" +
                        "values (?, ?, ?, ?, ?::app_users_role_enum);",
                Statement.RETURN_GENERATED_KEYS
        )) {
            ps.setBoolean(1, user.getActive());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getDisplayName());
            ps.setString(5, user.getRole());

            ps.executeUpdate();
            Long id;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getLong("id");
                } else {
                    throw new SQLException("Can't find id in ResultSet");
                }
            }
            user.setId(id);
            return user;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteAppUserById(Long id) {
        try (PreparedStatement ps = connection.prepareStatement("delete from app_users where id = ?;")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
