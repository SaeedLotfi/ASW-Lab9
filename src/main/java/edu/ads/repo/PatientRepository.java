package edu.ads.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.ads.domain.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
  Optional<Patient> findByPatientNo(String patientNo);

  List<Patient> findAllByOrderByLastNameAsc();

  @Query("""
      select p from Patient p left join p.address a
      order by a.city asc nulls last
      """)
  List<Patient> findAllOrderByAddressCityAsc();

  @Query("""
      select p from Patient p left join p.address a
      where lower(p.firstName) like lower(concat('%', :q, '%'))
      or lower(p.lastName) like lower(concat('%', :q, '%'))
      or lower(p.patientNo) like lower(concat('%', :q, '%'))
      or lower(a.city) like lower(concat('%', :q, '%'))
      or lower(a.postcode) like lower(concat('%', :q, '%'))
      """)
  List<Patient> search(@Param("q") String q);
}
