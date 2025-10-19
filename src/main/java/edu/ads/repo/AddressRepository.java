package edu.ads.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ads.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {}

