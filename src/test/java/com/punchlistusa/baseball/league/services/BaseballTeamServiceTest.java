package com.punchlistusa.baseball.league.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import com.punchlistusa.baseball.league.dto.requests.BaseballTeamRequest;
import com.punchlistusa.baseball.league.dto.responses.BaseballTeamDetailResponse;
import com.punchlistusa.baseball.league.entities.BaseballTeam;
import com.punchlistusa.baseball.league.exceptions.ApplicationException;
import com.punchlistusa.baseball.league.exceptions.ConflictException;
import com.punchlistusa.baseball.league.exceptions.NotFoundException;
import com.punchlistusa.baseball.league.exceptions.TechnicalException;
import com.punchlistusa.baseball.league.repositories.BaseballTeamRepository;
import com.punchlistusa.baseball.league.repositories.PlayerRepository;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class BaseballTeamServiceTest {

  private static final Long ID = 1L;
  private static final Long NO_VALUE = 0L;
  private static final Long PLAYERS = 10L;
  private static final Long PLAYER_ACTIVE = 5L;

  private static final String NAME = "Team";
  private static final String NAME_NEW = "new Name";

  private static final Sort BY = Sort.by(Direction.ASC, "name");

  private BaseballTeamService baseballTeamService;

  @Mock
  private BaseballTeamRepository baseballTeamRepository;
  @Mock
  private PlayerRepository playerRepository;



  @BeforeEach
  public void beforeEach() {
    baseballTeamService = new BaseballTeamService(baseballTeamRepository, playerRepository);

  }

  @Test
  void getBaseballTeamWithPlayerCountsTest() {
    try {
      List<BaseballTeam> teams =
          Collections.singletonList(BaseballTeam.builder().id(ID).name(NAME).build());
      when(baseballTeamRepository.findAll(BY)).thenReturn(teams);
      var result = baseballTeamService.getBaseballTeamWithPlayerCounts();
      assertFalse(result.isEmpty());
      BaseballTeamDetailResponse response = result.get(0);
      assertEquals(ID, response.getId());
      assertEquals(NAME, response.getName());
      assertEquals(NO_VALUE, response.getTotalPlayers());
      assertEquals(NO_VALUE, response.getTotalActivePlayers());

    } catch (ApplicationException e) {
      fail();
    }

  }

  @Test
  void getBaseballTeamWithPlayerCountsWithPlayersTest() {
    try {
      List<BaseballTeam> teams =
          Collections.singletonList(BaseballTeam.builder().id(ID).name(NAME).build());
      when(baseballTeamRepository.findAll(BY)).thenReturn(teams);
      List<BaseballTeamDetailResponse> groups = Collections.singletonList(BaseballTeamDetailResponse
          .builder().id(ID).totalPlayers(PLAYERS).totalActivePlayers(PLAYER_ACTIVE).build());
      when(playerRepository.findGroupingByTeam()).thenReturn(groups);
      var result = baseballTeamService.getBaseballTeamWithPlayerCounts();
      assertFalse(result.isEmpty());
      BaseballTeamDetailResponse response = result.get(0);
      assertEquals(ID, response.getId());
      assertEquals(NAME, response.getName());
      assertEquals(PLAYERS, response.getTotalPlayers());
      assertEquals(PLAYER_ACTIVE, response.getTotalActivePlayers());

    } catch (ApplicationException e) {
      fail();
    }

  }


  @SuppressWarnings("serial")
  @Test
  void getBaseballTeamWithPlayerCountsThrowsDataAccessException() throws ApplicationException {
    when(baseballTeamRepository.findAll(BY)).thenThrow(new DataAccessException("Error") {});

    Assertions.assertThrows(TechnicalException.class,
        () -> baseballTeamService.getBaseballTeamWithPlayerCounts());
    verify(baseballTeamRepository, times(1)).findAll(BY);
  }

  @Test
  void saveBaseballTeamTest() {
    try {
      when(baseballTeamRepository.findByName(anyString())).thenReturn(Optional.empty());

      BaseballTeam team = BaseballTeam.builder().id(ID).name(NAME).build();
      when(baseballTeamRepository.save(any())).thenReturn(team);
      var result =
          baseballTeamService.saveBaseballTeam(BaseballTeamRequest.builder().name(NAME).build());
      assertNotNull(result);
      assertEquals(ID, result.getId());
      assertEquals(NAME, result.getName());
      verify(baseballTeamRepository, times(1)).save(any());
    } catch (ApplicationException e) {
      fail();
    }

  }

  @Test
  void saveBaseballTeamWthDuplicatedNameTest() {
    BaseballTeam team = BaseballTeam.builder().id(ID).name(NAME).build();
    when(baseballTeamRepository.findByName(anyString())).thenReturn(Optional.of(team));


    Assertions.assertThrows(ConflictException.class, () -> baseballTeamService
        .saveBaseballTeam(BaseballTeamRequest.builder().name(NAME).build()));
    verify(baseballTeamRepository, times(1)).findByName(NAME);
    verify(baseballTeamRepository, never()).save(any());


  }

  @SuppressWarnings("serial")
  @Test
  void saveBaseballTeamThrowsDataAccessException() throws ApplicationException {
    when(baseballTeamRepository.findByName(anyString()))
        .thenThrow(new DataAccessException("Error") {});

    Assertions.assertThrows(TechnicalException.class, () -> baseballTeamService
        .saveBaseballTeam(BaseballTeamRequest.builder().name(NAME).build()));
    verify(baseballTeamRepository, times(1)).findByName(NAME);
  }

  @Test
  void updateBaseballTeamTest() {
    try {
      when(baseballTeamRepository.findByNameAndNotEquaslId(NAME_NEW, ID))
          .thenReturn(Optional.empty());

      BaseballTeam team = BaseballTeam.builder().id(ID).name(NAME).build();
      when(baseballTeamRepository.findById(ID)).thenReturn(Optional.of(team));
      BaseballTeam newTeam = BaseballTeam.builder().id(ID).name(NAME_NEW).build();

      when(baseballTeamRepository.save(any())).thenReturn(newTeam);
      var result = baseballTeamService.updateBaseballTeam(ID,
          BaseballTeamRequest.builder().name(NAME_NEW).build());
      assertNotNull(result);
      assertEquals(ID, result.getId());
      assertEquals(NAME_NEW, result.getName());
      verify(baseballTeamRepository, times(1)).save(any());
    } catch (ApplicationException e) {
      fail();
    }

  }

  @Test
  void updatBaseballTeamWithDuplicatedNameTest() {
    BaseballTeam team = BaseballTeam.builder().id(ID).name(NAME).build();
    when(baseballTeamRepository.findByNameAndNotEquaslId(NAME_NEW, ID))
        .thenReturn(Optional.of(team));


    Assertions.assertThrows(ConflictException.class, () -> baseballTeamService
        .updateBaseballTeam(ID, BaseballTeamRequest.builder().name(NAME_NEW).build()));
    verify(baseballTeamRepository, times(1)).findByNameAndNotEquaslId(NAME_NEW, ID);
    verify(baseballTeamRepository, never()).save(any());


  }

  @Test
  void updatBaseballTeamWithIdNotFoundTest() {
    when(baseballTeamRepository.findByNameAndNotEquaslId(NAME_NEW, ID))
        .thenReturn(Optional.empty());
    when(baseballTeamRepository.findById(ID)).thenReturn(Optional.empty());

    Assertions.assertThrows(NotFoundException.class, () -> baseballTeamService
        .updateBaseballTeam(ID, BaseballTeamRequest.builder().name(NAME_NEW).build()));
    verify(baseballTeamRepository, times(1)).findByNameAndNotEquaslId(NAME_NEW, ID);
    verify(baseballTeamRepository, never()).save(any());


  }

  @SuppressWarnings("serial")
  @Test
  void updateBaseballTeamThrowsDataAccessException() throws ApplicationException {
    when(baseballTeamRepository.findByNameAndNotEquaslId(NAME_NEW, ID))
        .thenThrow(new DataAccessException("Error") {});

    Assertions.assertThrows(TechnicalException.class, () -> baseballTeamService
        .updateBaseballTeam(ID, BaseballTeamRequest.builder().name(NAME_NEW).build()));
    verify(baseballTeamRepository, times(1)).findByNameAndNotEquaslId(NAME_NEW, ID);
  }

  @Test
  void getBaseballTeamByIdNotFoundTest() {
    when(baseballTeamRepository.findById(ID)).thenReturn(Optional.empty());

    Assertions.assertThrows(NotFoundException.class,
        () -> baseballTeamService.getBaseballTeamById(ID));
    verify(baseballTeamRepository, times(1)).findById(ID);


  }

  @Test
  void getBaseballTeamByIdFoundTest() {
    try {
      BaseballTeam team = BaseballTeam.builder().id(ID).name(NAME).build();
      when(baseballTeamRepository.findById(ID)).thenReturn(Optional.of(team));

      var result = baseballTeamService.getBaseballTeamById(ID);
      assertNotNull(result);
      assertEquals(ID, result.getId());
      assertEquals(NAME, result.getName());
      verify(baseballTeamRepository, times(1)).findById(ID);

    } catch (ApplicationException e) {
      fail();
    }
  }


  @SuppressWarnings("serial")
  @Test
  void getBaseballTeamByIdThrowsDataAccessException() throws ApplicationException {
    when(baseballTeamRepository.findById(ID)).thenThrow(new DataAccessException("Error") {});

    Assertions.assertThrows(TechnicalException.class,
        () -> baseballTeamService.getBaseballTeamById(ID));
    verify(baseballTeamRepository, times(1)).findById(ID);
  }

}
