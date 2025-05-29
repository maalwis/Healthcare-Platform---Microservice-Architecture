package com.healthcareplatform.PharmacyService.prescription;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * Represents a prescription order issued by a provider for a patient.
 */

@Entity
@Table(name = "prescriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "patient_first_name", length = 200)
    private String patientFirstName;

    @Column(name = "patient_second_name", length = 200)
    private String patientSecondName;

    @Column(name = "patient_date_of_birth")
    private LocalDateTime patientDateOfBirth;

    @Column(name = "patient_gender", length = 1)
    private String patientGender;

    @Column(name = "patient_contactInfo")
    private String patientContactInfo;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "doctor_first_name", length = 100, nullable = false)
    private String doctorFirstName;

    @Column(name = "doctor_last_name", length = 100, nullable = false)
    private String doctorLastName;

    @Column(name = "role", nullable = false, length = 50)
    private String doctorRole;

    @Column(name = "date_issued", nullable = false)
    private LocalDateTime dateIssued;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(columnDefinition = "jsonb")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

}