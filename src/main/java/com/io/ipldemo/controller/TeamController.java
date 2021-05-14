package com.io.ipldemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.io.ipldemo.model.Match;
import com.io.ipldemo.model.Team;
import com.io.ipldemo.service.MatchService;
import com.io.ipldemo.service.TeamService;

@RestController
public class TeamController {

	@Autowired
	private TeamService teamService;
	
	@Autowired
	private MatchService matchService;

	//  http://localhost:8080/team/Chennai%20Super%20Kings
	@GetMapping("/team/{teamName}") 
	public Team getTeamName(@PathVariable String teamName){
		Team team =  teamService.getTeamByName(teamName);
		List<Match> matches = matchService.getMatchesByName(teamName, teamName);
		team.setMatches(matches);
		
		return team;
	}
}
