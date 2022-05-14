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
import com.punchlistusa.baseball.league.entities.BaseballTeam;
import com.punchlistusa.baseball.league.repositories.BaseballTeamRepository;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class BaseballTeamControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private BaseballTeamRepository baseballTeamRepository;



  @BeforeEach
  public void beforeEach() {
    baseballTeamRepository.save(BaseballTeam.builder().name("Test0").build());
    baseballTeamRepository.save(BaseballTeam.builder().name("Test1").build());
  }

  @Test
  void getBaseballTeamsTest() {
    try {
      var expectedJson =
          "[{\"id\":1,\"name\":\"Test0\",\"totalActivePlayers\":0,\"totalPlayers\":0},{\"id\":2,\"name\":\"Test1\",\"totalActivePlayers\":0,\"totalPlayers\":0}]";
      final var request = MockMvcRequestBuilders.get("/api/baseball-team/")
          .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
      var result = mockMvc.perform(request).andDo(print()).andExpect(status().isOk()).andReturn();
      String actualJson = result.getResponse().getContentAsString();

      assertEquals(expectedJson, actualJson);
    } catch (final Exception e) {
      fail();
    }
  }



  @Test
  void saveBaseballTeamTest() {
    try {

      var body = new HashMap<String, Object>();

      body.put("name", "abc");
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
      ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
      var requestJson = ow.writeValueAsString(body);


      var expectedJson = "{\"id\":3,\"name\":\"abc\"}";

      var request = MockMvcRequestBuilders.post("/api/baseball-team/")

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
  void updateBaseballTeamTest() {
    try {

      var body = new HashMap<String, Object>();

      body.put("name", "abc");
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
      ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
      var requestJson = ow.writeValueAsString(body);


      var expectedJson = "{\"id\":1,\"name\":\"abc\"}";

      var request = MockMvcRequestBuilders.put("/api/baseball-team/1")

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
  void getBaseballTeamByIdTest() {
    try {

      var expectedJson = "{\"id\":1,\"name\":\"Test0\"}";
      var request = MockMvcRequestBuilders.get("/api/baseball-team/1")
          .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
      var result = mockMvc.perform(request).andDo(print()).andExpect(status().isOk()).andReturn();
      String actualJson = result.getResponse().getContentAsString();
      assertEquals(expectedJson, actualJson);
    } catch (Exception e) {
      fail();
    }
  }
}
