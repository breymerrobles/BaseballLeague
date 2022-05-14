package com.punchlistusa.baseball.league.services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.punchlistusa.baseball.league.dto.requests.PlayerRequest;
import com.punchlistusa.baseball.league.dto.responses.BaseballTeamResponse;
import com.punchlistusa.baseball.league.dto.responses.PlayerResponse;
import com.punchlistusa.baseball.league.entities.BaseballTeam;
import com.punchlistusa.baseball.league.entities.Player;
import com.punchlistusa.baseball.league.exceptions.ApplicationException;
import com.punchlistusa.baseball.league.exceptions.NotFoundException;
import com.punchlistusa.baseball.league.exceptions.TechnicalException;
import com.punchlistusa.baseball.league.repositories.BaseballTeamRepository;
import com.punchlistusa.baseball.league.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerService {
  private static final String NOT_FOUND_MESSAGE = "PLayer with id: %d was not found";
  private static final String TEAM_NOT_FOUND_MESSAGE = "Baseball team with id: %d was not found";
  private final PlayerRepository playerRepository;
  private final BaseballTeamRepository baseballTeamRepository;

  public List<PlayerResponse> getAllPlayers() throws ApplicationException {
    try {
      var players = playerRepository.findAll(Sort.by(Direction.ASC, "name"));
      return players.stream().map(this::buildResponse).collect(Collectors.toList());
    } catch (DataAccessException e) {
      throw new TechnicalException("Error getting info about teams ", e);
    }
  }

  public PlayerResponse savePlayer(final PlayerRequest request) throws ApplicationException {
    try {

      Player player =
          Player.builder().name(request.getName().trim()).status(request.getPlayerStatus()).build();
      player = playerRepository.save(player);
      return buildResponse(player);
    } catch (DataAccessException e) {
      throw new TechnicalException("Error creating team ", e);
    }
  }



  public PlayerResponse updatePlayer(final Long id, final PlayerRequest request)
      throws ApplicationException {
    try {

      Player player = findPlayerByIdentifier(id);
      player.setName(request.getName().trim());
      player.setStatus(request.getPlayerStatus());
      player = playerRepository.save(player);
      return buildResponse(player);
    } catch (DataAccessException e) {
      throw new TechnicalException("Error update player", e);
    }
  }



  public PlayerResponse getPlayerById(final Long id) throws ApplicationException {
    try {

      Player player = findPlayerByIdentifier(id);

      return buildResponse(player);
    } catch (DataAccessException e) {
      throw new TechnicalException("Error getting player by id", e);
    }
  }


  public PlayerResponse associate(final Long playerId, final Long teamId)
      throws ApplicationException {


    try {

      Player player = findPlayerByIdentifier(playerId);
      BaseballTeam team = baseballTeamRepository.findById(teamId)
          .orElseThrow(() -> new NotFoundException(String.format(TEAM_NOT_FOUND_MESSAGE, teamId)));
      player.setTeam(team);
      player = playerRepository.save(player);
      return buildResponse(player);
    } catch (DataAccessException e) {
      throw new TechnicalException("Error associate player to a team", e);
    }
  }

  private Player findPlayerByIdentifier(final Long id) throws NotFoundException {
    return playerRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, id)));
  }

  private PlayerResponse buildResponse(final Player player) {
    return PlayerResponse.builder().playerStatus(player.getStatus()).playerId(player.getId())
        .playerName(player.getName()).team(buildTeamResponse(player.getTeam())).build();
  }

  private BaseballTeamResponse buildTeamResponse(final BaseballTeam team) {

    return Objects.isNull(team) ? null : new BaseballTeamResponse(team.getId(), team.getName());
  }
}
