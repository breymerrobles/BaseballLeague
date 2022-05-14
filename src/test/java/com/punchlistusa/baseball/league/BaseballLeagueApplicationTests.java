package com.punchlistusa.baseball.league;

import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.punchlistusa.baseball.league.services.PlayerService;


@SpringBootTest
@ActiveProfiles("test")
class BaseballLeagueApplicationTests {
  @Autowired
  private PlayerService playerService;

  @Test
  void contextLoadsTest() {
    
    assertNotNull(playerService);
  }

}
