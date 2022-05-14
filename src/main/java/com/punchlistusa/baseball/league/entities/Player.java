package com.punchlistusa.baseball.league.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.punchlistusa.baseball.league.PlayerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "player")
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@EqualsAndHashCode
public class Player {
  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "player_id")
  private Long id;
  @Column(name = "player_name", unique = true)
  private String name;
  @Enumerated(EnumType.STRING)
  @Column(name = "player_status")
  private PlayerStatus status;
  @JoinColumn(name = "team_id")
  @ManyToOne(fetch = FetchType.EAGER)
  private BaseballTeam team;
}
