package com.project.guestApp.controllers;

import com.project.guestApp.entities.Role;
import com.project.guestApp.entities.User;
import com.project.guestApp.repos.UserRepository;
import com.project.guestApp.services.RoleService;
import com.project.guestApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public RoleController(RoleService roleService, UserRepository userRepository, UserService userService) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRole();
    }

    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @GetMapping("/{name}")
    public Role getRoleByName(@PathVariable String name) {
        return roleService.getRoleByName(name);
    }

    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role updatedRole) {
        return roleService.updateRole(id, updatedRole);
    }

    @DeleteMapping("/{id}")
    public List<User> deleteRole(@PathVariable Long id) {
        List<User> users = userService.getAllUsers();


        //roleService.deleteRole(id);

        return users;
    }
}
