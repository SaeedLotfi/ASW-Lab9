package edu.ads.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ads.domain.Surgery;

public interface SurgeryRepository extends JpaRepository<Surgery, Long> {
  Optional<Surgery> findBySurgeryNo(String surgeryNo);
}
