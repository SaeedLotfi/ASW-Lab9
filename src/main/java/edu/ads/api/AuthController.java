package edu.ads.api;

import edu.ads.domain.Role;
import edu.ads.domain.User;
import edu.ads.dto.AuthRequest;
import edu.ads.dto.AuthResponse;
import edu.ads.dto.RegisterRequest;
import edu.ads.repo.RoleRepository;
import edu.ads.repo.UserRepository;
import edu.ads.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authManager;
  private final JwtUtil jwtUtil;
  private final UserRepository users;
  private final RoleRepository roles;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest req) {
    var auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
    String token = jwtUtil.generateToken(auth);
    return ResponseEntity.ok(new AuthResponse(token));
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest req) {
    if (users.findByUsername(req.getUsername()).isPresent()) {
      return ResponseEntity.badRequest().build();
    }
    String roleName = "ROLE_" + (req.getRole() == null ? "RECEPTION" : req.getRole().toUpperCase());
    Role role = roles.findByName(roleName).orElseGet(() -> roles.save(Role.builder().name(roleName).build()));
    User user = User.builder()
        .username(req.getUsername())
        .password(passwordEncoder.encode(req.getPassword()))
        .enabled(true)
        .roles(Set.of(role))
        .build();
    users.save(user);

    var auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
    String token = jwtUtil.generateToken(auth);
    return ResponseEntity.ok(new AuthResponse(token));
  }
}
