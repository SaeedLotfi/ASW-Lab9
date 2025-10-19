package edu.ads.demo;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.ads.domain.Address;
import edu.ads.domain.Patient;
import edu.ads.repo.AppointmentRepository;
import edu.ads.repo.DentistRepository;
import edu.ads.repo.PatientRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CrudShowcase implements CommandLineRunner {

  private final AppointmentRepository appts;
  private final PatientRepository patients;
  private final DentistRepository dentists;

  @Override
  public void run(String... args) {
    // READ: list all appointments for Tony Smith
    System.out.println("=== Appointments for Dr. Smith ===");
    dentists.findByFirstNameAndLastName("Tony", "Smith").ifPresent(tony ->
      appts.findByDentistLastName("Smith").forEach(a ->
        System.out.printf("%s %s — %s %s (%s)%n",
          a.getDate(), a.getTime(),
          a.getPatient().getFirstName(), a.getPatient().getLastName(),
          a.getSurgery().getSurgeryNo()))
    );

    // CREATE: add a new patient and appointment
    Patient newP = patients.save(Patient.builder()
        .patientNo("P120").firstName("Ava").lastName("Stone")
        .address(Address.builder().line1("120 Lake Dr").city("Denton").state("TX").postcode("76208").build()).build());
    System.out.println("Created patient: " + newP.getPatientNo());

    // UPDATE: change patient last name
    newP.setLastName("Stone-River");
    patients.save(newP);
    System.out.println("Updated patient last name -> " + newP.getLastName());

    // DELETE: delete the new patient (cascades do NOT remove appointments unless you do it explicitly)
    patients.deleteById(newP.getId());
    System.out.println("Deleted patient P120");

    // READ by date
    System.out.println("=== 2013-09-12 Appointments ===");
    appts.findByDate(LocalDate.of(2013,9,12)).forEach(a ->
        System.out.printf("%s %s with %s %s at %s%n",
            a.getDate(), a.getTime(),
            a.getDentist().getFirstName(), a.getDentist().getLastName(),
            a.getSurgery().getSurgeryNo()));
  }
}
