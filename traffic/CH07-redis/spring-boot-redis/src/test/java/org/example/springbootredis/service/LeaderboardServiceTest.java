package org.example.springbootredis.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LeaderboardServiceTest {
    @Autowired
    private LeaderboardService leaderboardService;

    @Test
    void setLeaderboardOperationTest(){
        //given
        String userId = "user1";
        double score = 100.0;

        //when
        leaderboardService.addScore(userId, score);
        List<String> topPlayers = leaderboardService.getTopPlayers(1);
        Long rank = leaderboardService.getUserRank(userId);

        //then
        Assertions.assertFalse(topPlayers.isEmpty());
        Assertions.assertEquals(userId, topPlayers.get(0));
        Assertions.assertEquals(0L, rank);
    }

}