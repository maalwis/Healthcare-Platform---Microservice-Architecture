package com.healthcareplatform.AuthenticationService.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "user_profiles")
public class UserProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    // One-to-one relationship with the User entity.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    // Personal and professional information fields.
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String employeeId;

    @Column(nullable = false)
    private LocalDateTime hireDate;

    // Additional fields can be added as needed (e.g., contact information).

    // Getters and setters

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDateTime getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDateTime hireDate) {
        this.hireDate = hireDate;
    }
}
