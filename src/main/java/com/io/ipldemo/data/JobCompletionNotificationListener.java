package com.io.ipldemo.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.io.ipldemo.model.Team;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	// private final JdbcTemplate jdbcTemplate;

	private final EntityManager entityManager;

	/*
	 * @Autowired public JobCompletionNotificationListener(JdbcTemplate
	 * jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }
	 */

	@Autowired
	public JobCompletionNotificationListener(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			/*
			 * jdbcTemplate .query("SELECT team1, team2 from Match", (rs, row) ->
			 * "Team  1 - " + rs.getString(1) + "and  Team 2 - " + rs.getString(2))
			 * .forEach(str -> System.out.println(str));
			 */

			// Get distinct team information from team1
			Map<String, Team> teamData = new HashMap<>();
			entityManager.createQuery("select m.team1, count(*) from Match m group by m.team1 ", Object[].class).getResultList().stream()
					.map(e -> new Team((String) e[0], (long) e[1]))
					.forEach(team -> teamData.put(team.getTeamName(), team));

			// Get distinct team information from team2
			entityManager.createQuery("select m.team2, count(*) from Match m group by m.team2", Object[].class).getResultList().stream()
					.forEach(e -> {
						Team team = teamData.get((String) e[0]);
						team.setTotalMatches(team.getTotalMatches() + (long) e[1]); // Setting total matches played by a
																					// team (Aggregation 1st played and
																					// 2nd Played)
					});

			entityManager.createQuery("select m.winner, count(*) from Match m group by m.winner", Object[].class).getResultList()
					.stream().forEach(e -> {
						Team team = teamData.get((String) e[0]);
						if(team != null) {
							team.setTotalWins((long) e[1]);
						}
					});

			teamData.values().forEach(team -> entityManager.persist(team));
			
			// Print all team
			teamData.values().forEach(team -> System.out.println(team));

		}
	}
}