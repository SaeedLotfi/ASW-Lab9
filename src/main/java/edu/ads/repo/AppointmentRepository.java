package edu.ads.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ads.domain.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
  List<Appointment> findByDate(LocalDate date);
  List<Appointment> findByDentistLastName(String lastName);
}
