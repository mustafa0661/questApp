package com.project.guestApp.controllers;

import com.project.guestApp.entities.Role;
import com.project.guestApp.entities.User;
import com.project.guestApp.responses.UserResponse;
import com.project.guestApp.services.RoleService;
import com.project.guestApp.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Tag(name = "Kullanıcı İşlemleri", description = "Kullanıcı oluşturma, listeleme, silme gibi işlemleri içerir.")
public class UserController {

    private final RoleService roleService;
    private UserService userService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    @Operation(
            summary = "Tüm kullanıcıları getir",
            description = "Sistemdeki tüm kullanıcıları getirir."
    )
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers().stream().map(UserResponse::new).toList();
    }

    @PostMapping
    @Operation(
            summary = "Yeni Bir Kullanıcı Ekle",
            description = "Sİsteme yeni bir kullanıcı ekler."
    )
    public User createUser(@RequestBody User newUser) {
        return userService.saveOneUser(newUser);
    }

    @GetMapping("/{userId}")
    @Operation(
            summary = "Belirli bir kullanıcıyı getir",
            description = "ID'si verilen kullanıcıyı döndürür."
    )
    public UserResponse getOneUser(@PathVariable Long userId) {
        //custom exception
        User user = userService.getOneUserById(userId);
        return new UserResponse(user);
    }

    @PutMapping("/{userId}")
    @Operation(
            summary = "Kullanıcıyı Güncelle",
            description = "ID'si verilen kullanıcıyı günceller."
    )
    public User updateOneUser(@PathVariable Long userId, @RequestBody User newUser) {
        return userService.updateOneUser(userId, newUser);
    }

    @DeleteMapping("/{userId}")
    @Operation(
            summary = "Kullanıcıyı Sil",
            description = "ID'si verilen kullanıcıyı siler."
    )
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteOneUser(userId);
    }

    @PostMapping("/{userId}/roles")
    @Operation(
            summary = "Kullanıcıya Rol Atama",
            description = "ID'si verilen kullanıcıya rol ataması yapar."
    )
    public ResponseEntity<?> assignRoleToUser(@PathVariable Long userId, @RequestBody Map<String, String> request) {
        String roleName = request.get("roleName");
        Role role = roleService.getRoleByName(roleName);
        if (role == null) return ResponseEntity.badRequest().body("Role not found");

        User updatedUser = userService.assignRoleToUser(userId, role);
        if (updatedUser == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(updatedUser);
    }
}
