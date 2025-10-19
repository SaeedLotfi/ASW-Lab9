package edu.ads.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Surgery {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String surgeryNo; // e.g., S10, S13, S15

  @OneToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "address_id", nullable = false, unique = true)
  private Address address;
}
