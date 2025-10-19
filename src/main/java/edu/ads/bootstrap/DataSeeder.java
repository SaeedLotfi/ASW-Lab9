package edu.ads.bootstrap;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.ads.domain.Address;
import edu.ads.domain.Appointment;
import edu.ads.domain.Dentist;
import edu.ads.domain.Patient;
import edu.ads.domain.Role;
import edu.ads.domain.Surgery;
import edu.ads.domain.User;
import edu.ads.repo.AppointmentRepository;
import edu.ads.repo.DentistRepository;
import edu.ads.repo.PatientRepository;
import edu.ads.repo.RoleRepository;
import edu.ads.repo.SurgeryRepository;
import edu.ads.repo.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

  private final PatientRepository patients;
  private final DentistRepository dentists;
  private final SurgeryRepository surgeries;
  private final AppointmentRepository appts;
  private final RoleRepository roles;
  private final UserRepository users;

  @Override
  public void run(String... args) {
    if (appts.count() > 0) return; // already seeded

    // roles + user
    Role manager = roles.save(Role.builder().name("ROLE_MANAGER").build());
    Role receptionist = roles.save(Role.builder().name("ROLE_RECEPTION").build());
    users.save(User.builder().username("admin").password("{noop}admin").enabled(true)
        .roles(Set.of(manager, receptionist)).build());

    // surgeries
    Surgery s10 = surgeries.save(Surgery.builder()
        .surgeryNo("S10")
        .address(Address.builder().line1("10 Main St").city("Denton").state("TX").postcode("76201").build())
        .build());
    Surgery s13 = surgeries.save(Surgery.builder()
        .surgeryNo("S13")
        .address(Address.builder().line1("13 Oak Ave").city("Denton").state("TX").postcode("76202").build())
        .build());
    Surgery s15 = surgeries.save(Surgery.builder()
        .surgeryNo("S15")
        .address(Address.builder().line1("15 Pine Rd").city("Denton").state("TX").postcode("76203").build())
        .build());

    // dentists
    Dentist tony = dentists.save(Dentist.builder().firstName("Tony").lastName("Smith").build());
    Dentist helen = dentists.save(Dentist.builder().firstName("Helen").lastName("Pearson").build());
    Dentist robin = dentists.save(Dentist.builder().firstName("Robin").lastName("Plevin").build());

    // patients (each with address)
    Patient p100 = patients.save(Patient.builder().patientNo("P100").firstName("Gillian").lastName("White")
        .address(Address.builder().line1("100 Elm St").city("Denton").state("TX").postcode("76204").build()).build());
    Patient p105 = patients.save(Patient.builder().patientNo("P105").firstName("Jill").lastName("Bell")
        .address(Address.builder().line1("105 Maple St").city("Denton").state("TX").postcode("76205").build()).build());
    Patient p108 = patients.save(Patient.builder().patientNo("P108").firstName("Ian").lastName("MacKay")
        .address(Address.builder().line1("108 Cedar St").city("Denton").state("TX").postcode("76206").build()).build());
    Patient p110 = patients.save(Patient.builder().patientNo("P110").firstName("John").lastName("Walker")
        .address(Address.builder().line1("110 Birch St").city("Denton").state("TX").postcode("76207").build()).build());

    // appointments (from the screenshot)
    LocalDate d1 = LocalDate.of(2013, 9, 12);
    LocalDate d2 = LocalDate.of(2013, 9, 14);
    LocalDate d3 = LocalDate.of(2013, 9, 15);

    appts.save(Appointment.builder().date(d1).time(LocalTime.of(10,0)).dentist(tony).patient(p100).surgery(s15).build());
    appts.save(Appointment.builder().date(d1).time(LocalTime.of(12,0)).dentist(tony).patient(p105).surgery(s15).build());
    appts.save(Appointment.builder().date(d1).time(LocalTime.of(10,0)).dentist(helen).patient(p108).surgery(s10).build());
    appts.save(Appointment.builder().date(d2).time(LocalTime.of(14,0)).dentist(helen).patient(p108).surgery(s10).build());
    appts.save(Appointment.builder().date(d2).time(LocalTime.of(16,30)).dentist(robin).patient(p105).surgery(s15).build());
    appts.save(Appointment.builder().date(d3).time(LocalTime.of(18,0)).dentist(robin).patient(p110).surgery(s13).build());
  }
}
