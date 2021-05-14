package com.io.ipldemo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.io.ipldemo.model.Match;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {
	// It identifies the fields Team1 and Team2 from method getBy|Team1|Or|Team2|
	// Also it sorts automatically based on Date in Descending order
	// getByTeam1OrTeam2|OrderBy|Date|Desc|
	List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);
}
