package com.punchlistusa.baseball.league.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.punchlistusa.baseball.league.PlayerStatus;
import com.punchlistusa.baseball.league.entities.BaseballTeam;
import com.punchlistusa.baseball.league.entities.Player;
import com.punchlistusa.baseball.league.repositories.BaseballTeamRepository;
import com.punchlistusa.baseball.league.repositories.PlayerRepository;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class PlayerControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private BaseballTeamRepository baseballTeamRepository;
  @Autowired
  private PlayerRepository playerRepository;



  @BeforeEach
  public void beforeEach() {
    baseballTeamRepository.save(BaseballTeam.builder().name("Test0").build());
    playerRepository.save(Player.builder().name("player1").status(PlayerStatus.ACTIVE).build());

  }

  @Test
  void getPlayersTest() {
    try {
      var expectedJson =
          "[{\"playerId\":1,\"playerName\":\"player1\",\"playerStatus\":\"ACTIVE\",\"team\":null}]";
      final var request = MockMvcRequestBuilders.get("/api/player/")
          .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
      var result = mockMvc.perform(request).andDo(print()).andExpect(status().isOk()).andReturn();
      String actualJson = result.getResponse().getContentAsString();

      assertEquals(expectedJson, actualJson);
    } catch (final Exception e) {
      fail();
    }
  }



  @Test
  void savePlayerTest() {
    try {

      var body = new HashMap<String, Object>();

      body.put("name", "abc");
      body.put("playerStatus", PlayerStatus.ACTIVE.name());
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
      ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
      var requestJson = ow.writeValueAsString(body);
      var expectedJson =
          "{\"playerId\":2,\"playerName\":\"abc\",\"playerStatus\":\"ACTIVE\",\"team\":null}";
      var request = MockMvcRequestBuilders.post("/api/player/")

          .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
          .content(requestJson);
      var result = mockMvc.perform(request).andDo(print()).andExpect(status().isOk()).andReturn();
      String actualJson = result.getResponse().getContentAsString();
      assertEquals(expectedJson, actualJson);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void updatePlayerTest() {
    try {

      var body = new HashMap<String, Object>();

      body.put("name", "abc");
      body.put("playerStatus", PlayerStatus.INACTIVE.name());
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
      ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
      var requestJson = ow.writeValueAsString(body);
      var expectedJson =
          "{\"playerId\":1,\"playerName\":\"abc\",\"playerStatus\":\"INACTIVE\",\"team\":null}";

      var request = MockMvcRequestBuilders.patch("/api/player/1")

          .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
          .content(requestJson);
      var result = mockMvc.perform(request).andDo(print()).andExpect(status().isOk()).andReturn();
      String actualJson = result.getResponse().getContentAsString();
      assertEquals(expectedJson, actualJson);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void getPlayerByIdTest() {
    try {

      var expectedJson =
          "{\"playerId\":1,\"playerName\":\"player1\",\"playerStatus\":\"ACTIVE\",\"team\":null}";
      var request = MockMvcRequestBuilders.get("/api/player/1").accept(MediaType.APPLICATION_JSON)
          .contentType(MediaType.APPLICATION_JSON);
      var result = mockMvc.perform(request).andDo(print()).andExpect(status().isOk()).andReturn();
      String actualJson = result.getResponse().getContentAsString();
      assertEquals(expectedJson, actualJson);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void getPlayerByIdNotFoundTest() {
    try {

      var request = MockMvcRequestBuilders.get("/api/player/2").accept(MediaType.APPLICATION_JSON)
          .contentType(MediaType.APPLICATION_JSON);
      mockMvc.perform(request).andDo(print()).andExpect(status().isNotFound()).andReturn();

    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void associatePlayerToTeamTest() {
    try {


      var expectedJson =
          "{\"playerId\":1,\"playerName\":\"player1\",\"playerStatus\":\"ACTIVE\",\"team\":{\"id\":1,\"name\":\"Test0\"}}";

      var request = MockMvcRequestBuilders.patch("/api/player/1/baseball-team/1")

          .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("{}");
      var result = mockMvc.perform(request).andDo(print()).andExpect(status().isOk()).andReturn();
      String actualJson = result.getResponse().getContentAsString();
      assertEquals(expectedJson, actualJson);
    } catch (Exception e) {
      fail();
    }
  }
}
