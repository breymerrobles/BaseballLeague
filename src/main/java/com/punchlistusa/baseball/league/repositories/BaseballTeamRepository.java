package com.punchlistusa.baseball.league.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.punchlistusa.baseball.league.entities.BaseballTeam;
@Repository
public interface BaseballTeamRepository  extends JpaRepository<BaseballTeam, Long>{
  @Query("SELECT team FROM BaseballTeam team WHERE upper(team.name) = upper(:name)")
  Optional<BaseballTeam> findByName(@Param("name") final String name);
  
  @Query("SELECT team FROM BaseballTeam team WHERE upper(team.name) = upper(:name) AND team.id != :id")
  Optional<BaseballTeam> findByNameAndNotEquaslId(@Param("name")  final String name, @Param("id")final Long id);

}
