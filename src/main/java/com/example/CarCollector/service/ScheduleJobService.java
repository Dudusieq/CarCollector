package com.example.CarCollector.service;

import com.example.CarCollector.model.RefreshToken;
import com.example.CarCollector.repository.RefreshTokenRepository;
import com.example.CarCollector.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleJobService {

    private static final Logger log = LoggerFactory.getLogger(ScheduleJobService.class);

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public ScheduleJobService(RefreshTokenRepository refreshTokenRepository,
                                UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository         = userRepository;
    }

    // co godzine usuwa wygasly token
    @Scheduled(cron = "0 0 * * * *", zone = "Europe/Warsaw")
    public void cleanupExpiredRefreshTokens() {
        log.info("CRON: cleanupExpiredRefreshTokens() - checking for expired tokens...");
        LocalDateTime now = LocalDateTime.now();

        // silnie typowana lista RefreshToken
        List<RefreshToken> expired = refreshTokenRepository.findAllByExpiryDateBefore(now);
        refreshTokenRepository.deleteAll(expired);

        log.info("CRON: Removed {} expired refresh tokens", expired.size());
    }


    // co 30min rejestruje statystyki uzytkownikow
    @Scheduled(cron = "0 */30 * * * *", zone = "Europe/Warsaw")
    public void logUserStatistics() {
        long totalUsers  = userRepository.count();
        long activeUsers = userRepository.countByActiveTrue();
        log.info("CRON: logUserStatistics() - total users: {}, active users: {}", totalUsers, activeUsers);
    }
}
