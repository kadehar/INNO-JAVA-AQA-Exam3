package com.github.kadehar.inno.db.dao.impl;

import com.github.kadehar.inno.db.dao.EmployeeDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeDaoJdbc implements EmployeeDao {

    private final Connection connection;

    public EmployeeDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void deleteEmployeeById(Long id) {
        try (PreparedStatement ps = connection.prepareStatement("delete from employee where id = ?;")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
