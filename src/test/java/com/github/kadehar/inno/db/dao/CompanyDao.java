package com.github.kadehar.inno.db.dao;

import com.github.kadehar.inno.db.entity.CompanyEntity;

public interface CompanyDao {
    CompanyEntity create(CompanyEntity company);
    void deleteCompanyById(Long id);
}
