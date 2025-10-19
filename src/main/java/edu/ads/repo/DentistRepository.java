package edu.ads.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ads.domain.Dentist;

public interface DentistRepository extends JpaRepository<Dentist, Long> {
  Optional<Dentist> findByFirstNameAndLastName(String f, String l);
}
