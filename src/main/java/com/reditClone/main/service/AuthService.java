package com.reditClone.main.service;

import java.time.Instant; 
import org.springframework.security.core.Authentication;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.reditClone.main.dto.AuthenticationResponse;
import com.reditClone.main.dto.LoginRequest;
import com.reditClone.main.dto.RefreshTokenRequest;
import com.reditClone.main.dto.RegisterRequest;
import com.reditClone.main.exceptions.SpringRedditException;
import com.reditClone.main.models.NotificationEmail;
import com.reditClone.main.models.User;
import com.reditClone.main.models.VerificationToken;
import com.reditClone.main.repository.UserRepository;
import com.reditClone.main.repository.VerificationTokenRepository;
import com.reditClone.main.security.JwtProvider;

@Service
public class AuthService {
// AuthService is responsible to save and authenticate the user to database

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserRepository userRepository;
	@Autowired
	VerificationTokenRepository verificationTokenRepository;
	@Autowired
	private MailService mailService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	private RefreshTokenService refreshTokenService;

	@Transactional // because we are interacting with RDBMS
	public void signUp(RegisterRequest registerRequest) {
		// map the RegisterRequest to user model
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);

		userRepository.save(user);

		String token = generateVerificationToken(user);
		mailService.sendMail(new NotificationEmail("Please Activate your Account", user.getEmail(),
				"Thank you for signing up to Spring Reddit, "
						+ "please click on the below url to activate your account : "
						+ "http://localhost:8080/api/auth/accountVerification/" + token));
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepository.save(verificationToken);
		return token;
	}

	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken = this.verificationTokenRepository.findByToken(token);
		verificationToken.orElseThrow(() -> new SpringRedditException("Invalid token"));
		fetchUserEnable(verificationToken.get());
	}

	@Transactional
	public void fetchUserEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new SpringRedditException("Invalid User Name"));
		user.setEnabled(true); // now user is verified
		userRepository.save(user);
	}

	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token = jwtProvider.generateToken(authenticate);
		return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
	}
	public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
