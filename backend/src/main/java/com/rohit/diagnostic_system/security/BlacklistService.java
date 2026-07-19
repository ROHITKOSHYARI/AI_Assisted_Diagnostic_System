package com.rohit.diagnostic_system.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlacklistService {
    private static final String BLACKLIST_VALUE = "true";
    private static final String KEY_PREFIX = "blacklist";

    private final StringRedisTemplate redisTemplate;

    public void blacklistUser(UUID userId, String email) {
        blacklistPrincipal("PATIENT", userId, email);
    }

    public void removeUserFromBlacklist(UUID userId, String email) {
        removePrincipalFromBlacklist("PATIENT", userId, email);
    }

    public void blacklistPrincipal(String role, UUID userId, String email) {
        if (userId != null) {
            redisTemplate.opsForValue().set(userIdKey(role, userId), BLACKLIST_VALUE);
        }

        if (StringUtils.hasText(email)) {
            redisTemplate.opsForValue().set(emailKey(role, email), BLACKLIST_VALUE);
        }

        log.info("Principal blacklisted role={} userId={} email={}", role, userId, email);
    }

    public void removePrincipalFromBlacklist(String role, UUID userId, String email) {
        if (userId != null) {
            redisTemplate.delete(userIdKey(role, userId));
        }

        if (StringUtils.hasText(email)) {
            redisTemplate.delete(emailKey(role, email));
        }

        log.info("Principal removed from blacklist role={} userId={} email={}", role, userId, email);
    }

    public boolean isBlacklisted(Claims claims) {
        String role = claims.get("role", String.class);
        String email = claims.getSubject();
        String userId = claims.get("userId", String.class);

        if (StringUtils.hasText(role) && StringUtils.hasText(userId)
                && Boolean.TRUE.equals(redisTemplate.hasKey(userIdKey(role, UUID.fromString(userId))))) {
            return true;
        }

        return StringUtils.hasText(role) && StringUtils.hasText(email)
                && Boolean.TRUE.equals(redisTemplate.hasKey(emailKey(role, email)));
    }

    public boolean isUserBlacklisted(UUID userId, String email) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(userIdKey("PATIENT", userId)))
                || Boolean.TRUE.equals(redisTemplate.hasKey(emailKey("PATIENT", email)));
    }

    private String userIdKey(String role, UUID userId) {
        return KEY_PREFIX + ":" + role + ":id:" + userId;
    }

    private String emailKey(String role, String email) {
        return KEY_PREFIX + ":" + role + ":email:" + email.toLowerCase();
    }
}
