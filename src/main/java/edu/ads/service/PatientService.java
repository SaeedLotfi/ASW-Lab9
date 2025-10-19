package edu.ads.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.ads.domain.Patient;
import edu.ads.exception.ResourceNotFoundException;
import edu.ads.repo.PatientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {
  private final PatientRepository patients;

  public List<Patient> findAllSortedByLastName() {
    return patients.findAllByOrderByLastNameAsc();
  }

  public Patient getById(Long id) {
    return patients.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient id=" + id + " not found"));
  }

  public Patient create(Patient p) {
    // id must be null so JPA creates a new row
    p.setId(null);
    return patients.save(p);
  }

  public Patient update(Long id, Patient incoming) {
    Patient existing = getById(id);
    existing.setFirstName(incoming.getFirstName());
    existing.setLastName(incoming.getLastName());
    existing.setPatientNo(incoming.getPatientNo());
    // replace/merge address (since Patient.address is cascade=ALL)
    existing.setAddress(incoming.getAddress());
    return patients.save(existing);
  }

  public void delete(Long id) {
    Patient existing = getById(id);
    patients.delete(existing);
  }

  public List<Patient> search(String q) {
    return patients.search(q);
  }
}
