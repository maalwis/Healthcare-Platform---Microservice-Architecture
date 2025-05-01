package com.healthcareplatform.PharmacyService.service;

import com.healthcareplatform.PharmacyService.dto.PrescriptionDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PharmacyService {
    public PrescriptionDto getPrescriptionById(UUID prescriptionId){
        return null;
    };

    public PrescriptionDto createPrescription(@Valid PrescriptionDto prescription){
        return null;
    };
    public void fillPrescription(UUID prescriptionId){
        return;
    };

    public List<PrescriptionDto> getAllPrescriptions(){
        return null;
    };

}
