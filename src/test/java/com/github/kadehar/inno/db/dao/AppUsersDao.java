package com.github.kadehar.inno.db.dao;

import com.github.kadehar.inno.db.entity.AppUserEntity;

public interface AppUsersDao {
    AppUserEntity create(AppUserEntity user);
    void deleteAppUserById(Long id);
}
