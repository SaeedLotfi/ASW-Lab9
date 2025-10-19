package edu.ads.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Address {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String line1;
  private String line2;
  private String city;
  private String state;
  private String postcode;
}
