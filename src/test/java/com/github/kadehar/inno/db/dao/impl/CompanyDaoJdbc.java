package com.github.kadehar.inno.db.dao.impl;

import com.github.kadehar.inno.db.dao.CompanyDao;
import com.github.kadehar.inno.db.entity.CompanyEntity;

import java.sql.*;

public class CompanyDaoJdbc implements CompanyDao {

    private final Connection connection;

    public CompanyDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public CompanyEntity create(CompanyEntity company) {
        try (PreparedStatement ps = connection.prepareStatement(
                "insert into company(is_active, \"name\", description)" +
                        "values (?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS
        )) {
            ps.setBoolean(1, company.getActive());
            ps.setString(2, company.getName());
            ps.setString(3, company.getDescription());

            ps.executeUpdate();
            Long id;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getLong("id");
                } else {
                    throw new SQLException("Can't find id in ResultSet");
                }
            }
            company.setId(id);
            return company;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteCompanyById(Long id) {
        try (PreparedStatement ps = connection.prepareStatement("delete from company where id = ?;")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
