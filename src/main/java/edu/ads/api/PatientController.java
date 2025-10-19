package edu.ads.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import edu.ads.domain.Patient;
import edu.ads.service.PatientService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PatientController {

  private final PatientService patientService;

  // 1) GET /adsweb/api/v1/patients — all patients with addresses, sorted by
  // lastName asc
  @GetMapping("/patients")
  public List<Patient> getAllPatients() {
    return patientService.findAllSortedByLastName();
  }

  // 2) GET /adsweb/api/v1/patients/{id}
  @GetMapping("/patients/{id}")
  public Patient getPatient(@PathVariable Long id) {
    return patientService.getById(id);
  }

  // 3) POST /adsweb/api/v1/patients
  @PostMapping("/patients")
  public ResponseEntity<Patient> createPatient(@Validated @RequestBody Patient body) {
    Patient created = patientService.create(body);
    return ResponseEntity.created(URI.create("/adsweb/api/v1/patients/" + created.getId())).body(created);
  }

  // 4) PUT /adsweb/api/v1/patient/{id}
  @PutMapping("/patient/{id}")
  public Patient updatePatient(@PathVariable Long id, @Validated @RequestBody Patient body) {
    return patientService.update(id, body);
  }

  // 5) DELETE /adsweb/api/v1/patient/{id}
  @DeleteMapping("/patient/{id}")
  public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
    patientService.delete(id);
    return ResponseEntity.noContent().build();
  }

  // 6) GET /adsweb/api/v1/patient/search/{searchString}
  @GetMapping("/patient/search/{q}")
  public List<Patient> search(@PathVariable("q") String q) {
    return patientService.search(q);
  }
}
