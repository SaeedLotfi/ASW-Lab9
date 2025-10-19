package edu.ads.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Patient {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String patientNo;      // e.g., P100 (unique)
  @Column(nullable = false) private String firstName;
  @Column(nullable = false) private String lastName;

  @OneToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "address_id", nullable = false, unique = true)
  private Address address;
}