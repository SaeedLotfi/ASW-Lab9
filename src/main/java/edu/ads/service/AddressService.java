package edu.ads.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.ads.repo.PatientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {
  private final PatientRepository patients;

  public record AddressWithPatient(Long addressId, String line1, String line2, String city, String state,
      String postcode,
      Long patientId, String patientNo, String firstName, String lastName) {
  }

  public List<AddressWithPatient> allPatientAddressesSortedByCity() {
    return patients.findAllOrderByAddressCityAsc().stream()
        .map(p -> new AddressWithPatient(
            p.getAddress().getId(),
            p.getAddress().getLine1(),
            p.getAddress().getLine2(),
            p.getAddress().getCity(),
            p.getAddress().getState(),
            p.getAddress().getPostcode(),
            p.getId(),
            p.getPatientNo(),
            p.getFirstName(),
            p.getLastName()))
        .toList();
  }
}
