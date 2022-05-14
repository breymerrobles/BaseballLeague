package com.punchlistusa.baseball.league.services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.punchlistusa.baseball.league.dto.requests.BaseballTeamRequest;
import com.punchlistusa.baseball.league.dto.responses.BaseballTeamDetailResponse;
import com.punchlistusa.baseball.league.dto.responses.BaseballTeamResponse;
import com.punchlistusa.baseball.league.entities.BaseballTeam;
import com.punchlistusa.baseball.league.exceptions.ApplicationException;
import com.punchlistusa.baseball.league.exceptions.ConflictException;
import com.punchlistusa.baseball.league.exceptions.NotFoundException;
import com.punchlistusa.baseball.league.exceptions.TechnicalException;
import com.punchlistusa.baseball.league.repositories.BaseballTeamRepository;
import com.punchlistusa.baseball.league.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BaseballTeamService {
  private static final String BASEBALL_TEAM_ALREADY_EXIST = "Baseball team already exist";
  private static final String NOT_FOUND_MESSAGE = "Baseball team with id: %d was not found";
  private final BaseballTeamRepository baseballTeamRepository;
  private final PlayerRepository playerRepository;

  public List<BaseballTeamDetailResponse> getBaseballTeamWithPlayerCounts()
      throws ApplicationException {
    try {
      var teams = baseballTeamRepository.findAll(Sort.by(Direction.ASC, "name"));
      final var counterPlayers = playerRepository.findGroupingByTeam();
      return teams.stream().map(team -> {
        BaseballTeamDetailResponse counter =
            counterPlayers.stream().filter(cp -> cp.getId().equals(team.getId())).findFirst()
                .orElse(BaseballTeamDetailResponse.builder().build());
        return BaseballTeamDetailResponse.builder().id(team.getId()).name(team.getName())
            .totalActivePlayers(
                Objects.nonNull(counter.getTotalActivePlayers()) ? counter.getTotalActivePlayers()
                    : 0L)
            .totalPlayers(
                Objects.nonNull(counter.getTotalPlayers()) ? counter.getTotalPlayers() : 0L)
            .build();
      }).collect(Collectors.toList());
    } catch (DataAccessException e) {
      throw new TechnicalException("Error getting info about teams ", e);
    }
  }

  public BaseballTeamResponse saveBaseballTeam(final BaseballTeamRequest request)
      throws ApplicationException {
    try {
      if (baseballTeamRepository.findByName(request.getName().trim()).isPresent()) {
        throw new ConflictException(BASEBALL_TEAM_ALREADY_EXIST);
      }
      BaseballTeam team = BaseballTeam.builder().name(request.getName().trim()).build();
      team = baseballTeamRepository.save(team);
      return buildResponse(team);
    } catch (DataAccessException e) {
      throw new TechnicalException("Error creating team ", e);
    }
  }



  public BaseballTeamResponse updateBaseballTeam(final Long id, final BaseballTeamRequest request)
      throws ApplicationException {
    try {
      if (baseballTeamRepository.findByNameAndNotEquaslId(request.getName().trim(), id)
          .isPresent()) {
        throw new ConflictException(BASEBALL_TEAM_ALREADY_EXIST);
      }
      BaseballTeam team = baseballTeamRepository.findById(id)
          .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, id)));
      team.setName(request.getName().trim());
      team = baseballTeamRepository.save(team);
      return buildResponse(team);
    } catch (DataAccessException e) {
      throw new TechnicalException("Error update  team", e);
    }
  }

  public BaseballTeamResponse getBaseballTeamById(final Long id) throws ApplicationException {
    try {

      BaseballTeam team = baseballTeamRepository.findById(id)
          .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, id)));
      return buildResponse(team);
    } catch (DataAccessException e) {
      throw new TechnicalException("Error getting team by id", e);
    }
  }


  private BaseballTeamResponse buildResponse(final BaseballTeam team) {
    return new BaseballTeamResponse(team.getId(), team.getName());
  }
}
