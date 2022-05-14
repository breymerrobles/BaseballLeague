package com.punchlistusa.baseball.league.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "baseball_team")
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@EqualsAndHashCode
public class BaseballTeam {
  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "team_id")
  private Long id;
  @Column(name = "team_name", unique = true)
  private String name;
  @OneToMany(mappedBy = "team", fetch = FetchType.LAZY  )
  private List<Player> players;
}
