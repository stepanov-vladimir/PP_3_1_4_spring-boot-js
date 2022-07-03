package com.stepanov.springbootjs.service;

import com.stepanov.springbootjs.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();
}