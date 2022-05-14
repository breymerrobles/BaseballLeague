package com.punchlistusa.baseball.league.dto.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.punchlistusa.baseball.league.PlayerStatus;
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
public class PlayerRequest {
  @NotBlank
  private String name;
  @NotNull
  private PlayerStatus playerStatus;
}
