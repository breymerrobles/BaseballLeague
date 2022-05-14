package com.punchlistusa.baseball.league.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BaseballTeamDetailResponse extends BaseballTeamResponse {
  private Long totalActivePlayers;
  private Long totalPlayers;

  @Builder
  public BaseballTeamDetailResponse(final Long id, final String name, final Long totalActivePlayers,
      final Long totalPlayers) {
    super(id, name);
    this.totalActivePlayers = totalActivePlayers;
    this.totalPlayers = totalPlayers;
  }

  public BaseballTeamDetailResponse(final Long id, final String name) {
    super(id, name);
  }


  public BaseballTeamDetailResponse(final Long id, final Long totalActivePlayers,
      final Long totalPlayers) {
    super(id, null);
    this.totalActivePlayers = totalActivePlayers;
    this.totalPlayers = totalPlayers;
  }

}
