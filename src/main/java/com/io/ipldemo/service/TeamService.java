package com.io.ipldemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.io.ipldemo.model.Team;
import com.io.ipldemo.repository.TeamRepository;

@Service
public class TeamService {

	@Autowired
	private TeamRepository teamRepository;
	
	public Team getTeamByName(String team) {
		return teamRepository.findByTeamName(team);
	}
}
