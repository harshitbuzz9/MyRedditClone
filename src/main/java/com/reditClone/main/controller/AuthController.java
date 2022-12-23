package com.reditClone.main.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reditClone.main.dto.AuthenticationResponse;
import com.reditClone.main.dto.LoginRequest;
import com.reditClone.main.dto.RefreshTokenRequest;
import com.reditClone.main.dto.RegisterRequest;
import com.reditClone.main.service.AuthService;
import com.reditClone.main.service.RefreshTokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthService authService;
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) {
		this.authService.signUp(registerRequest);
		return new ResponseEntity<String>("User Registration success", HttpStatus.OK);
	}

	@GetMapping("/accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token) {
		this.authService.verifyAccount(token);
		return new ResponseEntity<String>("Ok ", HttpStatus.OK);
	}

	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
		return this.authService.login(loginRequest); 
	}
	
	@PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return new ResponseEntity<String>("successfully logged out",HttpStatus.OK);
    }

}
