package com.io.ipldemo.data;

import java.time.LocalDate;

import org.springframework.batch.item.ItemProcessor;

import com.io.ipldemo.model.Match;


public class MatchDataProcessor implements ItemProcessor<MatchData, Match> {

	@Override
	public Match process(MatchData matchInput) throws Exception {
		Match match = new Match();
		match.setId(Long.parseLong(matchInput.getId()));
		
		match.setCity(matchInput.getCity());
		match.setVenue(matchInput.getVenue());
		match.setDate(LocalDate.parse(matchInput.getDate()));
		match.setNeutralVenue(matchInput.getNeutral_venue());
		
		match.setPlayerOfMatch(matchInput.getPlayer_of_match());
		
		String firstInningsTeam, secondInningsTeam;
		if (matchInput.getToss_decision().equals("bat")) {
			firstInningsTeam = matchInput.getToss_winner();
			secondInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1()) ? matchInput.getTeam2()
					: matchInput.getTeam1();
		} else {
			secondInningsTeam = matchInput.getToss_winner();
			firstInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1()) ? matchInput.getTeam2()
					: matchInput.getTeam1();
		}
		
		match.setTeam1(firstInningsTeam);
		match.setTeam2(secondInningsTeam);
		
		match.setTossWinner(matchInput.getToss_winner());
		match.setTossDecision(matchInput.getToss_decision());
		
		match.setWinner(matchInput.getWinner());
		match.setResult(matchInput.getResult());
		match.setResultMargin(matchInput.getResult_margin());
		
		match.setEliminator(matchInput.getEliminator());
		match.setMethod(matchInput.getMethod());
		
		match.setUmpire1(matchInput.getUmpire1());
		match.setUmpire2(matchInput.getUmpire2());

		return match;
	}

}
