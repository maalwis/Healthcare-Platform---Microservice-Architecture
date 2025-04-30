package com.healthcareplatform.StaffService.service;

import com.healthcareplatform.StaffService.dto.StaffDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface StaffService {
    List<StaffDto> getAllStaff();

    StaffDto getStaffById(UUID staffId);

    StaffDto createStaff(@Valid StaffDto staff);

    StaffDto updateStaff(UUID staffId, @Valid StaffDto staffDto);

    void deleteStaff(UUID staffId);

}
