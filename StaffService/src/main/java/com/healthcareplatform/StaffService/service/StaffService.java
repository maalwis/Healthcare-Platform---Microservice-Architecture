package com.healthcareplatform.StaffService.service;

import com.healthcareplatform.StaffService.dto.StaffDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StaffService {
    public List<StaffDto> getAllStaff(){
        return null;
    };

    public StaffDto getStaffById(UUID staffId){
        return null;
    };

    public StaffDto createStaff(@Valid StaffDto staff){
        return null;
    };

    public StaffDto updateStaff(UUID staffId, @Valid StaffDto staffDto){
        return null;
    };

    public void deleteStaff(UUID staffId){
        return ;
    };

}
