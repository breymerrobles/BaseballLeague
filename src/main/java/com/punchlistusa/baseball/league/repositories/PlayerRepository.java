package com.punchlistusa.baseball.league.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.punchlistusa.baseball.league.dto.responses.BaseballTeamDetailResponse;
import com.punchlistusa.baseball.league.entities.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
  @Query("SELECT NEW com.punchlistusa.baseball.league.dto.responses.BaseballTeamDetailResponse(t.id, "
      + " SUM( CASE WHEN p.status = 'ACTIVE' THEN 1 ELSE 0 END), COUNT(p.id) )"
      + " FROM Player p  JOIN p.team t GROUP BY t")
  List<BaseballTeamDetailResponse> findGroupingByTeam();



}
