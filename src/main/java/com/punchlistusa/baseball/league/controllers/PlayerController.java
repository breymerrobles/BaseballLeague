package com.punchlistusa.baseball.league.controllers;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.punchlistusa.baseball.league.dto.requests.PlayerRequest;
import com.punchlistusa.baseball.league.dto.responses.PlayerResponse;
import com.punchlistusa.baseball.league.exceptions.ApplicationException;
import com.punchlistusa.baseball.league.services.PlayerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/player/")
@RequiredArgsConstructor
public class PlayerController {
  private final PlayerService playerService;


  @GetMapping
  public ResponseEntity<List<PlayerResponse>> getPlayers() throws ApplicationException {

    return ResponseEntity.ok(playerService.getAllPlayers());
  }

  @PostMapping
  public ResponseEntity<PlayerResponse> savePlayer(@Valid @RequestBody final PlayerRequest request)
      throws ApplicationException {

    return ResponseEntity.ok(playerService.savePlayer(request));
  }

  @PatchMapping("{id}")
  public ResponseEntity<PlayerResponse> updatePlayer(@PathVariable(required = true) final Long id,
      @Valid @RequestBody final PlayerRequest request) throws ApplicationException {

    return ResponseEntity.ok(playerService.updatePlayer(id, request));
  }

  @GetMapping("{id}")
  public ResponseEntity<PlayerResponse> getPlayerById(
      @PathVariable(required = true) final Long id) throws ApplicationException {

    return ResponseEntity.ok(playerService.getPlayerById(id));
  }
  
  @PatchMapping("{player-id}/baseball-team/{team-id}")
  public ResponseEntity<PlayerResponse> associatePlayerToTeam(@PathVariable(name ="player-id" ,required = true) final Long id,
      @PathVariable(name ="team-id" , required = true) final Long teamId) throws ApplicationException {

    return ResponseEntity.ok(playerService.associate(id, teamId));
  }

}
