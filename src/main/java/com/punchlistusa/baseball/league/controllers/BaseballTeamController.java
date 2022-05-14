package com.punchlistusa.baseball.league.controllers;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.punchlistusa.baseball.league.dto.requests.BaseballTeamRequest;
import com.punchlistusa.baseball.league.dto.responses.BaseballTeamDetailResponse;
import com.punchlistusa.baseball.league.dto.responses.BaseballTeamResponse;
import com.punchlistusa.baseball.league.exceptions.ApplicationException;
import com.punchlistusa.baseball.league.services.BaseballTeamService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/baseball-team/")
@RequiredArgsConstructor
public class BaseballTeamController {
  private final BaseballTeamService baseballTeamService;


  @GetMapping
  public ResponseEntity<List<BaseballTeamDetailResponse>> getBaseballTeams()
      throws ApplicationException {

    return ResponseEntity.ok(baseballTeamService.getBaseballTeamWithPlayerCounts());
  }

  @PostMapping
  public ResponseEntity<BaseballTeamResponse> saveBaseballTeam(
      @Valid @RequestBody final BaseballTeamRequest request) throws ApplicationException {

    return ResponseEntity.ok(baseballTeamService.saveBaseballTeam(request));
  }

  @PutMapping("{id}")
  public ResponseEntity<BaseballTeamResponse> updateBaseballTeam(
      @PathVariable(required = true) final Long id,
      @Valid @RequestBody final BaseballTeamRequest request) throws ApplicationException {

    return ResponseEntity.ok(baseballTeamService.updateBaseballTeam(id, request));
  }

  @GetMapping("{id}")
  public ResponseEntity<BaseballTeamResponse> getBaseballTeamById(
      @PathVariable(required = true) final Long id) throws ApplicationException {

    return ResponseEntity.ok(baseballTeamService.getBaseballTeamById(id));
  }

}
