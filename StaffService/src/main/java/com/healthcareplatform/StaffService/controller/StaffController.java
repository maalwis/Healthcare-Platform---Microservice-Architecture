package com.healthcareplatform.StaffService.controller;

import com.healthcareplatform.StaffService.dto.StaffDto;
import com.healthcareplatform.StaffService.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing hospital staff profiles.
 */
@RestController
@RequestMapping("/api/v1/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    /**
     * Retrieve a list of all staff members.
     *
     * TODO: Delegate to StaffService to retrieve all staff
     *
     * @return ResponseEntity containing a list of StaffDto objects and HTTP 200 status.
     */
    @GetMapping
    public ResponseEntity<List<StaffDto>> getAllStaff() {
        List<StaffDto> staff = staffService.getAllStaff();
        return ResponseEntity.ok(staff);
    }

    /**
     * Retrieve details for a specific staff member by ID.
     *
     * TODO: Delegate to StaffService to fetch staff by ID
     *
     * @param staffId Unique identifier of the staff member (path variable)
     * @return ResponseEntity containing StaffDto and HTTP 200 status if found;
     *         otherwise exception is propagated.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StaffDto> getStaffById(@PathVariable UUID staffId) {
        StaffDto staffMember = staffService.getStaffById(staffId);
        return ResponseEntity.ok(staffMember);
    }

    /**
     * Create a new staff profile.
     *
     * TODO: Delegate to StaffService to create staff
     *
     * @param staff Payload containing staff data (validated request body)
     * @return ResponseEntity containing created StaffDto and HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<StaffDto> createStaff(@Valid @RequestBody StaffDto staff) {
        StaffDto created = staffService.createStaff(staff);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Update an existing staff member's profile.
     *
     * TODO: Delegate to StaffService to update staff details
     *
     * @param staffId Unique identifier of the staff member (path variable)
     * @param staffDto Payload containing updated data (validated request body)
     * @return ResponseEntity containing updated StaffDto and HTTP 200 status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StaffDto> updateStaff(
            @PathVariable UUID staffId,
            @Valid @RequestBody StaffDto staffDto) {
        StaffDto updated = staffService.updateStaff(staffId, staffDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a staff profile by ID.
     *
     * TODO: Delegate to StaffService to delete staff
     *
     * @param staffId Unique identifier of the staff member (path variable)
     * @return ResponseEntity with HTTP 204 No Content on successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable UUID staffId) {
        staffService.deleteStaff(staffId);
        return ResponseEntity.noContent().build();
    }
}
