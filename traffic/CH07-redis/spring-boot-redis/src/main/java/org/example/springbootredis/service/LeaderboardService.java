package org.example.springbootredis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class LeaderboardService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String LEADERBODARD_KEY = "game:leaderboard";

    @Autowired
    public LeaderboardService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addScore(String userId, double score) {
        redisTemplate.opsForZSet().add(LEADERBODARD_KEY, userId, score);
        log.info("Score added for use: {} with score: {}", userId, score);
    }

    public List<String> getTopPlayers(int count) {
        Set<String> topScores = redisTemplate.opsForZSet().reverseRange(LEADERBODARD_KEY, 0, count - 1);
        return new ArrayList<>(topScores != null ? topScores : Collections.emptySet());
    }

    public Long getUserRank(String userId) {
        return redisTemplate.opsForZSet().reverseRank(LEADERBODARD_KEY, userId);
    }

    public Double getUserScore(String userId) {
        return redisTemplate.opsForZSet().score(LEADERBODARD_KEY, userId);
    }
}

