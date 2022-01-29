package com.shortener.service;

import com.shortener.entity.RoleUser;

public interface RoleAdminService {
    RoleUser findByRole (String role);
}
