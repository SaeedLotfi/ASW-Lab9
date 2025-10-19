package edu.ads.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ads.service.AddressService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AddressController {

  private final AddressService addressService;

  // 7) GET /adsweb/api/v1/addresses — all patient addresses + patient data,
  // sorted by city asc
  @GetMapping("/addresses")
  public List<AddressService.AddressWithPatient> getAllAddresses() {
    return addressService.allPatientAddressesSortedByCity();
  }
}
