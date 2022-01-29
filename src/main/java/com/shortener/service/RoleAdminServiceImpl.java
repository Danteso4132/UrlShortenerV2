package com.shortener.service;


import com.shortener.entity.RoleUser;
import com.shortener.repo.RoleAdminDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleAdminServiceImpl implements RoleAdminService {

    @Autowired
    RoleAdminDAO roleAdminDAO;

    @Override
    @Transactional
    public RoleUser findByRole(String name) {
        Optional<RoleUser> role = roleAdminDAO.findByRole(name);
        return role.orElse(null);
    }

}
