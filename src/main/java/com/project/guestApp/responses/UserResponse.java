package com.project.guestApp.responses;

import com.project.guestApp.entities.User;
import lombok.Data;

import java.util.List;
@Data
public class UserResponse {
    private Long id;
    private String userName;
    private List<String> roles;

    public UserResponse(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.roles = user.getRoles().stream().map(role -> role.getName()).toList();
    }
}
