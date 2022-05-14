package com.punchlistusa.baseball.league.dto.responses;

import com.punchlistusa.baseball.league.PlayerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class PlayerResponse {
  private Long playerId;
  private String playerName;
  private PlayerStatus playerStatus;
  private BaseballTeamResponse team;
}
