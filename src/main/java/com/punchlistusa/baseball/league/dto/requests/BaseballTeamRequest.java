package com.punchlistusa.baseball.league.dto.requests;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class BaseballTeamRequest {
  @NotBlank
  private String name;
}
