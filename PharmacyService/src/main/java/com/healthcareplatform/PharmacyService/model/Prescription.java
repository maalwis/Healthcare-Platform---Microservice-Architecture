package com.healthcareplatform.PharmacyService.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Represents a prescription order issued by a provider for a patient.
 *
 * <p>Stored in the “prescriptions” table with:
 * <ul>
 *   <li><b>patientId</b> linking to the patient’s UUID.</li>
 *   <li><b>doctorId</b> linking to the prescribing staff member’s UUID.</li>
 *   <li><b>dateIssued</b> recording when the prescription was generated.</li>
 *   <li><b>status</b> for tracking (“PENDING,” “FULFILLED,” “CANCELLED”).</li>
 *   <li><b>notes</b> as String containing free-form instructions or clinical remarks.</li>
 *   <li><b>createdAt</b> timestamp for auditing creation.</li>
 * </ul>
 *
 * <p>This entity decouples prescription metadata from line items, enabling providers to
 * manage orders and status separately from individual drug selections.
 *
 * <p>
 * Pharmacy Service Composition
 * <p>
 * Within the pharmacy domain, a Prescription is composed of one or more PrescriptionItem entries,
 * each linking to a specific medication and quantity. Here, Prescription is the aggregate root and
 * owns its PrescriptionItems via a @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
 * relationship. This composition guarantees that items cannot exist without their parent prescription;
 * creating a prescription automatically persists its items, and deleting the prescription cleans up
 * all related PrescriptionItem rows. By modeling this as composition, we maintain data integrity—every
 * drug order line is inherently tied to the lifecycle of its prescription.
 * */

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "doctor_id", nullable = false)
    private UUID doctorId;

    @Column(name = "date_issued", nullable = false)
    private OffsetDateTime dateIssued;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(columnDefinition = "jsonb")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public OffsetDateTime getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(OffsetDateTime dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}