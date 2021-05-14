package com.io.ipldemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.io.ipldemo.model.Match;
import com.io.ipldemo.repository.MatchRepository;

@Service
public class MatchService {

	@Autowired
	private MatchRepository matchRepository;
	
	public List<Match> getMatchesByName(String team1, String team2) {
		Pageable pageable = PageRequest.of(0, 4); // Returns top 4 from page 0
		return matchRepository.getByTeam1OrTeam2OrderByDateDesc(team1, team2, pageable);
	}
}
