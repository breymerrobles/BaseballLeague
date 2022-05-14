package com.punchlistusa.baseball.league.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BaseballTeamResponse {
  private Long id;
  private String name;
}
