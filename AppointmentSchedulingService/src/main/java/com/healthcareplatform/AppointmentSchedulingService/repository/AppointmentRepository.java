package com.healthcareplatform.AppointmentSchedulingService.repository;

import com.healthcareplatform.AppointmentSchedulingService.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}