package it.polito.tdp.provaFinale.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.provaFinale.model.DreamTeam;
import it.polito.tdp.provaFinale.model.Giocatore;
import it.polito.tdp.provaFinale.model.Squadra;
import it.polito.tdp.provaFinale.model.Statistiche;

public class ChampionsDAO {
	
	/**
	 * Metodo per leggere la lista di tutti i calciatori presenti nel database.
	 * @param playersIdMap
	 * @return {@code List<Giocatore>}
	 */
	public List<Giocatore> getAllPlayers(){
		String sql= "SELECT * FROM giocatori ORDER BY name";
		List<Giocatore> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new Giocatore(rs.getInt("IDGiocatore"), rs.getString("name"), rs.getString("position"), rs.getInt("age"), rs.getString("nationality"), rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in getAllPlayers");
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Metodo per leggere la lista di tutte le squadre presenti nel database.
	 * @param playersIdMap
	 * @return {@code List<Squadra>}
	 */
	public List<Squadra> getAllTeams(){
		String sql= "SELECT * FROM squadre ORDER BY club";
		List<Squadra> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new Squadra(rs.getString("club"), rs.getInt("total_match_played")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in getAllTeams");
			e.printStackTrace();
			return null;
		}
	}
	
	// Creazione Dream team senza nessun vincolo  
	
	// Portiere: // 
	/* saved / (conceded + saved) */
	public DreamTeam goalkeeperForSavedDividedConcededPlusSaved() {
		String sql="SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played, s.saved/(s.conceded + s.saved) AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`=\"Portiere\" AND s.minutes_played>= 540 "
				+ "ORDER BY stats DESC, s.saved desc";	
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			rs.first();
			DreamTeam risultato= new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
					rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "saved / (conceded + saved)", rs.getDouble("stats"));
					
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in goalkeeperForSavedDividedConcededPlusSaved");
			e.printStackTrace();
			return null;
		}
	}
	
	/* conceded / minutes played */
	public DreamTeam goalkeeperForConcededDividedMinutesPlayed() {
		String sql="SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.conceded/s.minutes_played AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`=\"Portiere\"  AND s.minutes_played>= 540 "
				+ "ORDER BY stats asc, s.minutes_played DESC";
	
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			rs.first();
			DreamTeam risultato= new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
					rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "conceded / minutes played", rs.getDouble("stats"));
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in goalkeeperForConcededDividedMinutesPlayed");
			e.printStackTrace();
			return null;
		}
	}

	/* clean sheets / match played */
	public DreamTeam goalkeeperForCleansheetsDividedMatchPlayed() {
		String sql="SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.cleansheets/s.match_played AS stats "
					+ "FROM giocatori g, statistiche s "
					+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`=\"Portiere\" AND s.minutes_played>= 540 "
					+ "ORDER BY stats desc, s.cleansheets DESC";
	
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			rs.first();
			DreamTeam risultato= new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
					rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "clean sheets / match played", rs.getDouble("stats"));			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in goalkeeperForCleansheetsDividedMatchPlayed");
			e.printStackTrace();
			return null;
		}
	}
	
	// Giocatori di movimento: //
	/*balls recoverd / minutes played*/
	public List<DreamTeam> playersForBallsRecoverdDividedMinutesPlayed(){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.balls_recoverd/s.minutes_played AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>= 540 "
				+ "ORDER BY stats desc, s.balls_recoverd desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "balls recoverd / minutes played", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForBallsRecoverdDividedMinutesPlayed");
			e.printStackTrace();
			return null;
		}
	}
	
	/*tackles won / tackles*/
	public List<DreamTeam> playersForTacklesWonDividedTackles(){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.tackles_won/s.tackles AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>= 540 "
				+ "ORDER BY stats DESC, s.tackles_won desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "tackles won / tackles", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForTacklesWonDividedTackles");
			e.printStackTrace();
			return null;
		}
	}
	
	/*pass completed / pass attempted*/
	public List<DreamTeam> playersForPassCompletedDividedPassAttempted(){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.pass_completed/s.pass_attempted AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>= 540 "
				+ "ORDER BY stats DESC, s.pass_completed desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "pass completed / pass attempted", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForPassCompletedDividedPassAttempted");
			e.printStackTrace();
			return null;
		}
	}
	
	/*assist / minutes played*/
	public List<DreamTeam> playersForAssistDividedMinutesPlayed(){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.assist/s.minutes_played AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>= 540 "
				+ "ORDER BY stats DESC, s.assist desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "assist / minutes played", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForAssistDividedMinutesPlayed");
			e.printStackTrace();
			return null;
		}
	}
	
	/*goals / minutes played*/
	public List<DreamTeam> playersForGoalsDividedMinutesPlayed(){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.goals/s.minutes_played AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>= 540 "
				+ "ORDER BY stats DESC, s.goals desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "goals / minutes played", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForGoalsDividedMinutesPlayed");
			e.printStackTrace();
			return null;
		}
	}
	
	/*dribbles / minutes played*/
	public List<DreamTeam> playersForDribblesDividedMinutesPlayed(){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.dribbles/s.minutes_played AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>= 540 "
				+ "ORDER BY stats DESC, s.dribbles desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "dribbles / minutes played", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForDribblesDividedMinutesPlayed");
			e.printStackTrace();
			return null;
		}
	}
	
	/*shot on target / shot attempts*/
	public List<DreamTeam> playersForShotOnTargetDividedShotAttempts(){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.shot_on_target/s.shot_attempts AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>= 540 "
				+ "ORDER BY stats DESC, s.shot_on_target desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "shot on target / shot attempts", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForShotOnTargetDividedShotAttempts");
			e.printStackTrace();
			return null;
		}
	}
	
	/*goals / shot attempts*/
	public List<DreamTeam> playersForGoalsDividedShotAttempts(){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played, s.goals/s.shot_attempts AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>= 540 "
				+ "ORDER BY stats DESC, s.goals desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "goals / shot attempts", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForGoalsDividedShotAttempts");
			e.printStackTrace();
			return null;
		}
	}
	
	
	// Creazione Dream Team con vincoli
	
	// Portiere: //
	/* saved / (conceded + saved) */
	public DreamTeam goalkeeperForSavedDividedConcededPlusSaved(int minMinutesPlayed, int minAge, int maxAge, double maxWage, double maxValue) {
		String sql="SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played, s.saved/(s.conceded + s.saved) AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`=\"Portiere\" AND s.minutes_played>=? AND g.age>= ? AND g.age<= ? AND g.`FIFA_wage_€`<= ? AND g.`FIFA_value_€`<= ? "
				+ "ORDER BY stats DESC, s.saved desc";	
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, minMinutesPlayed);
			st.setInt(2, minAge);
			st.setInt(3, maxAge);
			st.setDouble(4, maxWage);
			st.setDouble(5, maxValue);			
			
			ResultSet rs= st.executeQuery();
			
			if(rs.first()) {
				DreamTeam risultato= new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"),
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "saved / (conceded + saved)", rs.getDouble("stats"));
				conn.close();
				return risultato;
			}
				
			else {
				conn.close();
				return null;
			}			
						
			
		} catch (Exception e) {
			System.out.println("Errore in goalkeeperForSavedDividedConcededPlusSaved");
			e.printStackTrace();
			return null;
		}
	}
	
	/* conceded / minutes played */
	public DreamTeam goalkeeperForConcededDividedMinutesPlayed(int minMinutesPlayed, int minAge, int maxAge, double maxWage, double maxValue) {
		String sql="SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.conceded/s.minutes_played AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`=\"Portiere\" AND s.minutes_played>=? AND g.age>= ? AND g.age<= ? AND g.`FIFA_wage_€`<= ? AND g.`FIFA_value_€`<= ? "
				+ "ORDER BY stats asc, s.minutes_played DESC";
	
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, minMinutesPlayed);
			st.setInt(2, minAge);
			st.setInt(3, maxAge);
			st.setDouble(4, maxWage);
			st.setDouble(5, maxValue);			
			
			ResultSet rs= st.executeQuery();
			
			if(rs.first()) {
				DreamTeam risultato= new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"),
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "conceded / minutes played", rs.getDouble("stats"));
				conn.close();
				return risultato;
			}
				
			else {
				conn.close();
				return null;
			}		
			
		} catch (Exception e) {
			System.out.println("Errore in goalkeeperForConcededDividedMinutesPlayed");
			e.printStackTrace();
			return null;
		}
	}
	
	/* clean sheets / match played */
	public DreamTeam goalkeeperForCleansheetsDividedMatchPlayed(int minMinutesPlayed, int minAge, int maxAge, double maxWage, double maxValue) {
		String sql="SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.cleansheets/s.match_played AS stats "
					+ "FROM giocatori g, statistiche s "
					+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`=\"Portiere\" AND s.minutes_played>=? AND g.age>= ? AND g.age<= ? AND g.`FIFA_wage_€`<= ? AND g.`FIFA_value_€`<= ? "
					+ "ORDER BY stats desc, s.cleansheets DESC";
	
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, minMinutesPlayed);
			st.setInt(2, minAge);
			st.setInt(3, maxAge);
			st.setDouble(4, maxWage);
			st.setDouble(5, maxValue);			
			
			ResultSet rs= st.executeQuery();
			
			if(rs.first()) {
				DreamTeam risultato= new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"),
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "clean sheets / match played", rs.getDouble("stats"));
				conn.close();
				return risultato;
			}
				
			else {
				conn.close();
				return null;
			}			
			
		} catch (Exception e) {
			System.out.println("Errore in goalkeeperForCleansheetsDividedMatchPlayed");
			e.printStackTrace();
			return null;
		}
	}
	
	// Giocatori di movimento: //
	/*balls recoverd / minutes played*/
	public List<DreamTeam> playersForBallsRecoverdDividedMinutesPlayed(int minMinutesPlayed, int minAge, int maxAge, double maxWage, double maxValue){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.balls_recoverd/s.minutes_played AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>=? AND g.age>= ? AND g.age<= ? AND g.`FIFA_wage_€`< ? AND g.`FIFA_value_€`< ? "
				+ "AND s.balls_recoverd/s.minutes_played IS NOT NULL "
				+ "ORDER BY stats desc, s.balls_recoverd desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, minMinutesPlayed);
			st.setInt(2, minAge);
			st.setInt(3, maxAge);
			st.setDouble(4, maxWage);
			st.setDouble(5, maxValue);
			
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "balls recoverd / minutes played", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForBallsRecoverdDividedMinutesPlayed");
			e.printStackTrace();
			return null;
		}
	}
	
	/*tackles won / tackles*/
	public List<DreamTeam> playersForTacklesWonDividedTackles(int minMinutesPlayed, int minAge, int maxAge, double maxWage, double maxValue){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.tackles_won/s.tackles AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>=? AND g.age>= ? AND g.age<= ? AND g.`FIFA_wage_€`< ? AND g.`FIFA_value_€`< ? "
				+ "AND s.tackles_won/s.tackles IS NOT null "
				+ "ORDER BY stats DESC, s.tackles_won desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, minMinutesPlayed);
			st.setInt(2, minAge);
			st.setInt(3, maxAge);
			st.setDouble(4, maxWage);
			st.setDouble(5, maxValue);
			
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "tackles won / tackles", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForTacklesWonDividedTackles");
			e.printStackTrace();
			return null;
		}
	}
	
	/*pass completed / pass attempted*/
	public List<DreamTeam> playersForPassCompletedDividedPassAttempted(int minMinutesPlayed, int minAge, int maxAge, double maxWage, double maxValue){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.pass_completed/s.pass_attempted AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>=? AND g.age>= ? AND g.age<= ? AND g.`FIFA_wage_€`< ? AND g.`FIFA_value_€`< ? "
				+ "AND s.pass_completed/s.pass_attempted IS NOT null "
				+ "ORDER BY stats DESC, s.pass_completed desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, minMinutesPlayed);
			st.setInt(2, minAge);
			st.setInt(3, maxAge);
			st.setDouble(4, maxWage);
			st.setDouble(5, maxValue);
			
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "pass completed / pass attempted", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForPassCompletedDividedPassAttempted");
			e.printStackTrace();
			return null;
		}
	}
	
	/*assist / minutes played*/
	public List<DreamTeam> playersForAssistDividedMinutesPlayed(int minMinutesPlayed, int minAge, int maxAge, double maxWage, double maxValue){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.assist/s.minutes_played AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>=? AND g.age>= ? AND g.age<= ? AND g.`FIFA_wage_€`< ? AND g.`FIFA_value_€`< ? "
				+ "AND s.assist/s.minutes_played IS NOT null "
				+ "ORDER BY stats DESC, s.assist desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, minMinutesPlayed);
			st.setInt(2, minAge);
			st.setInt(3, maxAge);
			st.setDouble(4, maxWage);
			st.setDouble(5, maxValue);
			
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "assist / minutes played", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForAssistDividedMinutesPlayed");
			e.printStackTrace();
			return null;
		}
	}
	
	/*goals / minutes played*/
	public List<DreamTeam> playersForGoalsDividedMinutesPlayed(int minMinutesPlayed, int minAge, int maxAge, double maxWage, double maxValue){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.goals/s.minutes_played AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>=? AND g.age>= ? AND g.age<= ? AND g.`FIFA_wage_€`< ? AND g.`FIFA_value_€`< ? "
				+ "AND s.goals/s.minutes_played IS NOT null "
				+ "ORDER BY stats DESC, s.goals desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, minMinutesPlayed);
			st.setInt(2, minAge);
			st.setInt(3, maxAge);
			st.setDouble(4, maxWage);
			st.setDouble(5, maxValue);
			
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "goals / minutes played", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForGoalsDividedMinutesPlayed");
			e.printStackTrace();
			return null;
		}
	}
	
	/*dribbles / minutes played*/
	public List<DreamTeam> playersForDribblesDividedMinutesPlayed(int minMinutesPlayed, int minAge, int maxAge, double maxWage, double maxValue){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.dribbles/s.minutes_played AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>=? AND g.age>= ? AND g.age<= ? AND g.`FIFA_wage_€`< ? AND g.`FIFA_value_€`< ? "
				+ "AND s.dribbles/s.minutes_played IS NOT null "
				+ "ORDER BY stats DESC, s.dribbles desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, minMinutesPlayed);
			st.setInt(2, minAge);
			st.setInt(3, maxAge);
			st.setDouble(4, maxWage);
			st.setDouble(5, maxValue);
			
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "dribbles / minutes played", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForDribblesDividedMinutesPlayed");
			e.printStackTrace();
			return null;
		}
	}
	
	/*shot on target / shot attempts*/
	public List<DreamTeam> playersForShotOnTargetDividedShotAttempts(int minMinutesPlayed, int minAge, int maxAge, double maxWage, double maxValue){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played,s.shot_on_target/s.shot_attempts AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>=? AND g.age>= ? AND g.age<= ? AND g.`FIFA_wage_€`< ? AND g.`FIFA_value_€`< ? "
				+ "AND s.shot_on_target/s.shot_attempts IS NOT null "
				+ "ORDER BY stats DESC, s.shot_on_target desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, minMinutesPlayed);
			st.setInt(2, minAge);
			st.setInt(3, maxAge);
			st.setDouble(4, maxWage);
			st.setDouble(5, maxValue);
			
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "shot on target / shot attempts", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForShotOnTargetDividedShotAttempts");
			e.printStackTrace();
			return null;
		}
	}
	
	/*goals / shot attempts*/
	public List<DreamTeam> playersForGoalsDividedShotAttempts(int minMinutesPlayed, int minAge, int maxAge, double maxWage, double maxValue){
		String sql= "SELECT g.name, g.`position`, s.club, g.age, g.`FIFA_wage_€`, g.`FIFA_value_€`, s.minutes_played, s.goals/s.shot_attempts AS stats "
				+ "FROM giocatori g, statistiche s "
				+ "WHERE g.IDGiocatore= s.IDGiocatore AND g.`position`!=\"Portiere\" AND s.minutes_played>=? AND g.age>= ? AND g.age<= ? AND g.`FIFA_wage_€`< ? AND g.`FIFA_value_€`< ? "
				+ "AND s.goals/s.shot_attempts IS NOT null "
				+ "ORDER BY stats DESC, s.goals desc";
		List<DreamTeam> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, minMinutesPlayed);
			st.setInt(2, minAge);
			st.setInt(3, maxAge);
			st.setDouble(4, maxWage);
			st.setDouble(5, maxValue);
			
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) 				
				risultato.add(new DreamTeam(rs.getString("name"), rs.getString("position"), rs.getString("club"), rs.getInt("age"), 
						rs.getDouble("FIFA_wage_€"), rs.getDouble("FIFA_value_€"), rs.getInt("minutes_played"), "goals / shot attempts", rs.getDouble("stats")));
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in playersForGoalsDividedShotAttempts");
			e.printStackTrace();
			return null;
		}
	}
	
	public Statistiche getStatisticsGoalkeeper(int IDGiocatore) {
		String sql= "SELECT tab.* "
				+ "FROM (SELECT IDGiocatore, club, saved, RANK() OVER (ORDER BY  saved desc) AS s, saved/(conceded+saved), RANK() OVER (ORDER BY  saved/(conceded+saved) DESC) AS scs, "
				+ "conceded, RANK() OVER (ORDER BY CASE WHEN conceded IS NULL THEN 1 ELSE 0 END, conceded) AS c, "
				+ "conceded/minutes_played,RANK() OVER (ORDER BY CASE WHEN conceded/minutes_played IS NULL THEN 1 ELSE 0 END, conceded/minutes_played) AS cm, "
				+ "cleansheets, RANK() OVER (order BY cleansheets DESC) AS cl, "
				+ "minutes_played, match_played "
				+ "FROM statistiche) AS tab "
				+ "WHERE tab.IDGiocatore=?";
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, IDGiocatore);
	
			ResultSet rs= st.executeQuery();
			
			rs.first();		
			
			Statistiche stats= new Statistiche(IDGiocatore, rs.getString("club"), rs.getInt("saved"), rs.getInt("s"), 
					rs.getDouble("saved/(conceded+saved)"), rs.getInt("scs"), rs.getInt("conceded"), rs.getInt("c"),
					rs.getDouble("conceded/minutes_played"), rs.getInt("cm"), rs.getInt("cleansheets"), rs.getInt("cl"),
					rs.getInt("minutes_played"), rs.getInt("match_played"));
			
			
			conn.close();
			return stats;			
			
		} catch (Exception e) {
			System.out.println("Errore in getStatisticsPlayer");
			e.printStackTrace();
			return null;
		}
	}
	
	public Statistiche getStatisticsPlayer(int IDGiocatore) {
		String sql= "SELECT tab.* "
				+ "FROM (SELECT IDGiocatore, club, "
				+ "balls_recoverd, RANK() OVER (ORDER BY  balls_recoverd desc) AS b, balls_recoverd/minutes_played, RANK() OVER (ORDER BY  balls_recoverd/minutes_played DESC) AS bmp, "
				+ "tackles, RANK() OVER (ORDER BY  tackles desc) AS t, tackles_won, RANK() OVER (ORDER BY  tackles_won DESC) AS tw, tackles_won/tackles, RANK() OVER (ORDER BY  tackles_won/tackles DESC) AS twp, "
				+ "pass_attempted,RANK() OVER (ORDER BY  pass_attempted desc) AS pa, pass_completed,RANK() OVER (ORDER BY  pass_completed desc) AS pc, "
				+ "pass_completed/pass_attempted,RANK() OVER (ORDER BY  pass_completed desc) AS p, "
				+ "assist,RANK() OVER (ORDER BY  assist desc) AS a, assist/minutes_played,RANK() OVER (ORDER BY  assist/minutes_played desc) AS am, "
				+ "shot_attempts, RANK() OVER (order BY shot_attempts DESC) AS sa, shot_on_target, RANK() OVER (order BY shot_on_target DESC) AS so, "
				+ "shot_on_target/shot_attempts, RANK() OVER (order BY shot_on_target/shot_attempts DESC) AS s, "
				+ "goals,RANK() OVER (ORDER BY  goals desc) AS g, goals/shot_attempts,RANK() OVER (ORDER BY  goals/shot_attempts desc) AS gs, "
				+ "goals/minutes_played,RANK() OVER (ORDER BY  goals/minutes_played desc) AS gm, "
				+ "dribbles,RANK() OVER (ORDER BY  dribbles desc) AS d, dribbles/minutes_played,RANK() OVER (ORDER BY  dribbles/minutes_played desc) AS dm, "
				+ "minutes_played, match_played "
				+ "FROM statistiche) AS tab "
				+ "WHERE tab.IDGiocatore=?";
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, IDGiocatore);
	
			ResultSet rs= st.executeQuery();
			
			rs.first();		
			
			Statistiche stats= new Statistiche(IDGiocatore, rs.getString("club"), rs.getInt("balls_recoverd"), rs.getInt("b"), rs.getDouble("balls_recoverd/minutes_played"), rs.getInt("bmp"),
					rs.getInt("tackles"), rs.getInt("t"), rs.getInt("tackles_won"), rs.getInt("tw"), rs.getDouble("tackles_won/tackles"), rs.getInt("twp"),
					rs.getInt("pass_attempted"), rs.getInt("pa"), rs.getInt("pass_completed"), rs.getInt("pc"), 
					rs.getDouble("pass_completed/pass_attempted"), rs.getInt("p"), 
					rs.getInt("assist"), rs.getInt("a"), rs.getDouble("assist/minutes_played"), rs.getInt("am"),
					rs.getInt("shot_attempts"), rs.getInt("sa"), rs.getInt("shot_on_target"), rs.getInt("so"),
					rs.getDouble("shot_on_target/shot_attempts"), rs.getInt("s"), 
					rs.getInt("goals"), rs.getInt("g"), rs.getDouble("goals/shot_attempts"), rs.getInt("gs"), 
					rs.getDouble("goals/minutes_played"), rs.getInt("gm"),
					rs.getInt("dribbles"), rs.getInt("d"), rs.getDouble("dribbles/minutes_played"), rs.getInt("dm"),
					rs.getInt("minutes_played"), rs.getInt("match_played"));
			
			conn.close();
			return stats;			
			
		} catch (Exception e) {
			System.out.println("Errore in getStatisticsPlayer");
			e.printStackTrace();
			return null;
		}
	}
	
	public Statistiche getStatisticsClub(String team) {
		String sql= "SELECT * "
				+ "from(SELECT c.club, SUM(s.saved) AS \"saved\",  RANK() OVER (ORDER BY  SUM(s.saved) desc) AS s, "
				+ "SUM(s.saved)/SUM(s.conceded+s.saved) AS \"saved/(conceded+saved)\", RANK() OVER (ORDER BY  (SUM(s.saved)/SUM(s.conceded+s.saved)) DESC) AS scs, "
				+ "sum(s.conceded) AS \"conceded\", RANK() OVER (ORDER BY sum(s.conceded) ASC) AS c, "
				+ "sum(s.conceded)/c.total_match_played AS \"conceded/total_match_played\", RANK() OVER (ORDER BY sum(s.conceded)/c.total_match_played ASC) AS cm, "
				+ "SUM(s.cleansheets) AS \"cleansheets\", RANK() OVER (order BY SUM(s.cleansheets) DESC) AS cl, "
				+ "SUM(s.balls_recoverd) AS \"balls_recoverd\", RANK() OVER (ORDER BY  SUM(s.balls_recoverd) desc) AS b, "
				+ "SUM(s.balls_recoverd)/c.total_match_played AS \"balls_recoverd/total_match_played\", RANK() OVER (ORDER BY  SUM(s.balls_recoverd)/c.total_match_played DESC) AS bmp, "
				+ "SUM(s.tackles) AS \"tackles\", RANK() OVER (ORDER BY  SUM(s.tackles) desc) AS t, SUM(s.tackles_won) AS \"tackles_won\", RANK() OVER (ORDER BY  SUM(s.tackles_won) DESC) AS tw, "
				+ "SUM(s.tackles_won)/SUM(s.tackles) AS \"tackles_won/tackles\", RANK() OVER (ORDER BY  SUM(s.tackles_won)/SUM(s.tackles) DESC) AS twp, "
				+ "SUM(s.pass_attempted) AS \"pass_attempted\", RANK() OVER (ORDER BY  SUM(s.pass_attempted) desc) AS pa, "
				+ "SUM(s.pass_completed) AS \"pass_completed\",RANK() OVER (ORDER BY  SUM(s.pass_completed) desc) AS pc, "
				+ "SUM(s.pass_completed)/SUM(s.pass_attempted) AS \"pass_completed/pass_attempted\", RANK() OVER (ORDER BY  SUM(s.pass_completed)/SUM(s.pass_attempted) desc) AS p, "
				+ "SUM(s.assist) AS \"assist\",RANK() OVER (ORDER BY  SUM(s.assist) desc) AS a, "
				+ "SUM(s.assist)/c.total_match_played AS \"assist/total_match_played\", RANK() OVER (ORDER BY  SUM(s.assist)/c.total_match_played desc) AS am, "
				+ "SUM(s.shot_attempts) AS \"shot_attempts\", RANK() OVER (order BY SUM(s.shot_attempts) DESC) AS sa, "
				+ "SUM(s.shot_on_target) AS \"shot_on_target\", RANK() OVER (order BY SUM(s.shot_on_target) DESC) AS so, "
				+ "SUM(s.shot_on_target)/SUM(s.shot_attempts) AS \"shot_on_target/shot_attempts\", RANK() OVER (order BY SUM(s.shot_on_target)/SUM(s.shot_attempts) DESC) AS soa, "
				+ "SUM(s.goals) AS \"goals\", RANK() OVER (ORDER BY SUM(s.goals) desc) AS g, "
				+ "SUM(s.goals)/SUM(s.shot_attempts) AS \"goals/shot_attempts\", RANK() OVER (ORDER BY SUM(s.goals)/SUM(s.shot_attempts) desc) AS gs, "
				+ "SUM(s.goals)/c.total_match_played AS \"goals/total_match_played\",RANK() OVER (ORDER BY SUM(s.goals)/c.total_match_played desc) AS gm, "
				+ "SUM(s.dribbles) AS \"dribbles\",RANK() OVER (ORDER BY  SUM(s.dribbles) desc) AS d, "
				+ "SUM(s.dribbles)/c.total_match_played AS \"dribbles/total_match_played\",RANK() OVER (ORDER BY  SUM(s.dribbles)/c.total_match_played desc) AS dm, "
				+ "c.total_match_played "
				+ "FROM statistiche s, squadre c "
				+ "WHERE s.club= c.club "
				+ "GROUP BY s.club) AS tab "
				+ "WHERE tab.club= ?";
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setString(1, team);
	
			ResultSet rs= st.executeQuery();
			
			rs.first();		

			Statistiche stats= new Statistiche(rs.getString("club"), 
					rs.getInt("saved"), rs.getInt("s"), rs.getDouble("saved/(conceded+saved)"), rs.getInt("scs"),
					rs.getInt("conceded"), rs.getInt("c"), rs.getDouble("conceded/total_match_played"), rs.getInt("cm"),
					rs.getInt("cleansheets"), rs.getInt("cl"),
					rs.getInt("balls_recoverd"), rs.getInt("b"), rs.getDouble("balls_recoverd/total_match_played"), rs.getInt("bmp"),
					rs.getInt("tackles"), rs.getInt("t"), rs.getInt("tackles_won"), rs.getInt("tw"), rs.getDouble("tackles_won/tackles"), rs.getInt("twp"),
					rs.getInt("pass_attempted"), rs.getInt("pa"), rs.getInt("pass_completed"), rs.getInt("pc"),
					rs.getDouble("pass_completed/pass_attempted"), rs.getInt("p"),
					rs.getInt("assist"), rs.getInt("a"), rs.getDouble("assist/total_match_played"), rs.getInt("am"),
					rs.getInt("shot_attempts"), rs.getInt("sa"), rs.getInt("shot_on_target"), rs.getInt("so"),
					rs.getDouble("shot_on_target/shot_attempts"), rs.getInt("soa"),
					rs.getInt("goals"), rs.getInt("g"), rs.getDouble("goals/shot_attempts"), rs.getInt("gs"), 
					rs.getDouble("goals/total_match_played"), rs.getInt("gm"),
					rs.getInt("dribbles"), rs.getInt("d"), rs.getDouble("dribbles/total_match_played"), rs.getInt("dm"), 
					rs.getInt("total_match_played"));
			
			conn.close();
			return stats;			
			
		} catch (Exception e) {
			System.out.println("Errore in getStatisticsClub");
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer countPlayers(){
		String sql= "SELECT count(*) as tot FROM giocatori WHERE `position`!=\"Portiere\"";
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			rs.first();
			int risultato= rs.getInt("tot");
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in countPlayers");
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer countGoalkeeper(){
		String sql= "SELECT count(*) as tot FROM giocatori WHERE `position`= \"Portiere\"";
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			rs.first();
			int risultato= rs.getInt("tot");
			
			
			conn.close();
			return risultato;			
			
		} catch (Exception e) {
			System.out.println("Errore in countGoalkeeper");
			e.printStackTrace();
			return null;
		}
	}
	
}
