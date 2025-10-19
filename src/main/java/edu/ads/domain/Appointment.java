package edu.ads.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(indexes = {
  @Index(name = "ix_appt_date_time", columnList = "date,time"),
  @Index(name = "ix_appt_patient", columnList = "patient_id"),
  @Index(name = "ix_appt_dentist", columnList = "dentist_id"),
  @Index(name = "ix_appt_surgery", columnList = "surgery_id")
})
public class Appointment {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false) private java.time.LocalDate date;
  @Column(nullable = false) private java.time.LocalTime time;

  @ManyToOne(optional = false) @JoinColumn(name = "patient_id")
  private Patient patient;

  @ManyToOne(optional = false) @JoinColumn(name = "dentist_id")
  private Dentist dentist;

  @ManyToOne(optional = false) @JoinColumn(name = "surgery_id")
  private Surgery surgery;
}

