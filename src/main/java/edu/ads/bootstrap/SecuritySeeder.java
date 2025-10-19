package edu.ads.bootstrap;

import edu.ads.domain.Role;
import edu.ads.domain.User;
import edu.ads.repo.RoleRepository;
import edu.ads.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class SecuritySeeder implements CommandLineRunner {

  private final RoleRepository roles;
  private final UserRepository users;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) {
    Role manager = roles.findByName("ROLE_MANAGER")
        .orElseGet(() -> roles.save(Role.builder().name("ROLE_MANAGER").build()));
    Role reception = roles.findByName("ROLE_RECEPTION")
        .orElseGet(() -> roles.save(Role.builder().name("ROLE_RECEPTION").build()));

    // default users
    if (users.findByUsername("manager").isEmpty()) {
      users.save(User.builder()
          .username("manager")
          .password(passwordEncoder.encode("password"))
          .enabled(true)
          .roles(Set.of(manager))
          .build());
    }
    if (users.findByUsername("reception").isEmpty()) {
      users.save(User.builder()
          .username("reception")
          .password(passwordEncoder.encode("password"))
          .enabled(true)
          .roles(Set.of(reception))
          .build());
    }
  }
}
