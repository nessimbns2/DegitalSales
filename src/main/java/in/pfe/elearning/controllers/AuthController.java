package in.pfe.elearning.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import in.pfe.elearning.entity.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import in.pfe.elearning.entity.Role.ERole;
import in.pfe.elearning.payload.request.ForgotPasswordRequest;
import in.pfe.elearning.payload.request.LoginRequest;
import in.pfe.elearning.payload.request.ResetPasswordRequest;
import in.pfe.elearning.payload.request.SignupRequest;
import in.pfe.elearning.payload.response.JwtResponse;
import in.pfe.elearning.payload.response.MessageResponse;
import in.pfe.elearning.repository.PasswordResetTokenRepository;
import in.pfe.elearning.repository.RoleRepository;
import in.pfe.elearning.repository.UserRepository;
import in.pfe.elearning.security.jwt.JwtUtils;
import in.pfe.elearning.security.services.UserDetailsImpl;
import in.pfe.elearning.services.EmailService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
	private final PasswordResetTokenRepository passwordResetTokenRepository;
	private final EmailService emailService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder,PasswordResetTokenRepository passwordResetTokenRepository , EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.emailService = emailService;
    }

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());



		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(),
												 userDetails.getEmail(),
												 roles,
												 userDetails.getFirstName(),
												 userDetails.getLastName()
		));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		System.out.println(signUpRequest.getTel());

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		final User[] users = {null};
		// Create new user's account
		User user = new User(
							 signUpRequest.getFirstname(),
							 signUpRequest.getLastname(),
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()),
				             signUpRequest.getTel()
							 );

		Set<String> strRoles = signUpRequest.getRoles();
		List<Role> roles = new ArrayList();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);

		} else {

			strRoles.forEach(role -> {
				switch (role) {
				case "ROLE_ADMIN":
					Role modRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);
					users[0] = user;
					break;
				default:
					Role adminRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
				}
			});
		}
		if (signUpRequest.getRoles().contains("ROLE_USER")) {
			user.setRoles(roles);
			user.setClients(new ArrayList<Client>());
			userRepository.save(user);
		} 
		else {
			user.setRoles(roles);
			userRepository.save(users[0]);
		}
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
		Optional<User> userOptional = userRepository.findByEmail(forgotPasswordRequest.getEmail());
		if (!userOptional.isPresent()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User does not exist!"));
		}

		User user = userOptional.get();
		String token = UUID.randomUUID().toString();
		passwordResetTokenRepository.save(new PasswordResetToken(token, user));
		emailService.sendPasswordResetEmail(user.getEmail(), token);
		return ResponseEntity.ok(new MessageResponse("Password reset email sent!"));
	}

	@PostMapping("/reset-password")
public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
    Optional<PasswordResetToken> tokenOptional = passwordResetTokenRepository.findByToken(resetPasswordRequest.getToken());
    if (!tokenOptional.isPresent()) {
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid or expired token!"));
    }

    PasswordResetToken token = tokenOptional.get();


    if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Token has expired!"));
    }

    User user = token.getUser();
    user.setPassword(encoder.encode(resetPasswordRequest.getNewPassword()));
    userRepository.save(user);
    passwordResetTokenRepository.delete(token);

    return ResponseEntity.ok(new MessageResponse("Password reset successfully!"));
}


	@GetMapping("/user/{email}")
	public ResponseEntity<?> getUser(@PathVariable String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		if (!userOptional.isPresent()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User does not exist!"));
		}
		User user = userOptional.get();
		return ResponseEntity.ok(user);
	}










}
