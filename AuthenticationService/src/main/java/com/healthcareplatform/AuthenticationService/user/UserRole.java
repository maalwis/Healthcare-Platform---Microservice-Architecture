package com.healthcareplatform.AuthenticationService.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.healthcareplatform.AuthenticationService.role.Role;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "user_roles")
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonBackReference   // <-- omit the back-pointer when serializing
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    @JsonBackReference   // <-- omit the back-pointer when serializing
    private Role role;

    @Column(nullable = false)
    private LocalDateTime assignedAt;

    public UserRoleId getId() {
        return id;
    }

    public void setId(UserRoleId id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}
