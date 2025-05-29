package com.healthcareplatform.PharmacyService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffDto {

    private Long doctorId;

    private String doctorFirstName;

    private String doctorLastName;

    private String doctorRole;
}
