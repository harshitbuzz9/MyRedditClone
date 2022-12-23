package com.reditClone.main.service;

import java.time.Instant;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.reditClone.main.exceptions.SpringRedditException;
import com.reditClone.main.models.RefreshToken;
import com.reditClone.main.repository.RefreshTokenRepository;

@Service
@Transactional
public class RefreshTokenService {
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SpringRedditException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
