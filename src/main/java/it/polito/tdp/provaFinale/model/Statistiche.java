package it.polito.tdp.provaFinale.model;

import java.util.Objects;

public class Statistiche {
	private int IDGiocatore;
	private String club;
	
	// Portiere //
	private int saved;
	private int savedRank;
	private double savedDividedConcededPlusSaved;
	private int ranksavedDividedConcededPlusSaved;
	
	private int conceded;
	private int concededRank;
	/* singolo giocatore */
	private double concededDividedMinutesPlayed;
	private int rankConcededDividedMinutesPlayed;
	/* per squadra */
	private double concededDividedMatchPlayed;
	private int rankConcededDividedMatchPlayed;
	
	private int cleansheets;
	private int cleansheetsRank;
		
	// Giocatore di movimento
	// 1) Difesa
	private int ballsRecoverd;
	private int ballsRecoverdRank;
	/* singolo giocatore */
	private double ballsRecoverdDividedMinutesPlayed;
	private int rankBallsRecoverdDividedMinutesPlayed;
	/* per squadra */
	private double ballsRecoverdDividedMatchPlayed;
	private int rankBallsRecoverdDividedMatchPlayed;
	
	private int tackles;
	private int tacklesRank;
	private int tacklesWon;
	private int tacklesWonRank;
	private double tacklesWonDividedTackles;
	private int rankTacklesWonDividedTackles;
	
	// 2) Centrocampo	
	private int passAttempted;
	private int passAttemptedRank;
	private int passCompleted;
	private int passCompletedRank;
	private double passCompletedDividedPassAttempted;
	private int passCompletedDividedPassAttemptedRank;
	
	private int assist;
	private int rankAssist;
	/* singolo giocatore */
	private double assistDividedMinutesPlayed;
	private int rankAssistDividedMinutesPlayed;
	/* per squadra */
	private double assistDividedMatchPlayed;
	private int rankAssistDividedMatchPlayed;
	
	// 3) Attacco
	private int shotAttempts;
	private int rankShotAttempts;
	private int shotOnTarget;
	private int rankShotOnTarget;
	private double shotOnTargetDividedShotAttempts;
	private int shotOnTargetDividedShotAttemptsRank;
	
	private int goals;
	private int goalsRank;
	private double goalsDividedShotAttempts;
	private int goalsDividedShotAttemptsRank;
	/* singolo giocatore */
	private double goalsDividedMinutesPlayed;
	private int goalsDividedMinutesPlayedRank;
	/* per squadra */
	private double goalsDividedMatchPlayed;
	private int goalsDividedMatchPlayedRank;
	
	private int dribbles;
	private int dribblesRanK;
	/* singolo giocatore */
	private double dribblesDividedMinutesPlayed;
	private int dribblesDividedMinutesPlayedRank;
	/* per squadra */
	private double dribblesDividedMatchPlayed;
	private int dribblesDividedMatchPlayedRank;
	
	// Tempo giocato
	private int minutesPlayed;
	private int matchPlayed;
	
	/**
	 * Costruttore statistiche Giocatore: PORTIERE
	 * @param iDGiocatore
	 * @param club
	 * @param saved
	 * @param savedRank
	 * @param savedDividedConcededPlusSaved
	 * @param ranksavedDividedConcededPlusSaved
	 * @param conceded
	 * @param concededRank
	 * @param concededDividedMinutesPlayed
	 * @param rankConcededDividedMinutesPlayed
	 * @param cleansheets
	 * @param cleansheetsRank
	 * @param minutesPlayed
	 * @param matchPlayed
	 */
	
	public Statistiche(int iDGiocatore, String club, int saved, int savedRank,
			double savedDividedConcededPlusSaved, int ranksavedDividedConcededPlusSaved, int conceded, int concededRank,
			double concededDividedMinutesPlayed, int rankConcededDividedMinutesPlayed, int cleansheets,
			int cleansheetsRank, int minutesPlayed, int matchPlayed) {
		IDGiocatore = iDGiocatore;
		this.club = club;
		this.saved = saved;
		this.savedRank = savedRank;
		this.savedDividedConcededPlusSaved = savedDividedConcededPlusSaved;
		this.ranksavedDividedConcededPlusSaved = ranksavedDividedConcededPlusSaved;
		this.conceded = conceded;
		this.concededRank = concededRank;
		this.concededDividedMinutesPlayed = concededDividedMinutesPlayed;
		this.rankConcededDividedMinutesPlayed = rankConcededDividedMinutesPlayed;
		this.cleansheets = cleansheets;
		this.cleansheetsRank = cleansheetsRank;
		this.minutesPlayed = minutesPlayed;
		this.matchPlayed = matchPlayed;
	}
	
	/**
	 * Costruttore statistiche Giocatore: GIOCATORE DI MOVIMENTO	
	 * @param iDGiocatore
	 * @param club
	 * @param ballsRecoverd
	 * @param ballsRecoverdRank
	 * @param ballsRecoverdDividedMinutesPlayed
	 * @param rankBallsRecoverdDividedMinutesPlayed
	 * @param tackles
	 * @param tacklesRank
	 * @param tacklesWon
	 * @param tacklesWonRank
	 * @param tacklesWonDividedTackles
	 * @param rankTacklesWonDividedTackles
	 * @param passAttempted
	 * @param passAttemptedRank
	 * @param passCompleted
	 * @param passCompletedRank
	 * @param passCompletedDividedPassAttempted
	 * @param passCompletedDividedPassAttemptedRank
	 * @param assist
	 * @param rankAssist
	 * @param assistDividedMinutesPlayed
	 * @param rankAssistDividedMinutesPlayed
	 * @param shotAttempts
	 * @param rankShotAttempts
	 * @param shotOnTarget
	 * @param rankShotOnTarget
	 * @param shotOnTargetDividedShotAttempts
	 * @param shotOnTargetDividedShotAttemptsRank
	 * @param goals
	 * @param goalsRank
	 * @param goalsDividedShotAttempts
	 * @param goalsDividedShotAttemptsRank
	 * @param goalsDividedMinutesPlayed
	 * @param goalsDividedMinutesPlayedRank
	 * @param dribbles
	 * @param dribblesRanK
	 * @param dribblesDividedMinutesPlayed
	 * @param dribblesDividedMinutesPlayedRank
	 * @param minutesPlayed
	 * @param matchPlayed
	 */
	public Statistiche(int iDGiocatore, String club, int ballsRecoverd, int ballsRecoverdRank,
			double ballsRecoverdDividedMinutesPlayed, int rankBallsRecoverdDividedMinutesPlayed, int tackles,
			int tacklesRank, int tacklesWon, int tacklesWonRank, double tacklesWonDividedTackles,
			int rankTacklesWonDividedTackles, int passAttempted, int passAttemptedRank, int passCompleted,
			int passCompletedRank, double passCompletedDividedPassAttempted, int passCompletedDividedPassAttemptedRank,
			int assist, int rankAssist, double assistDividedMinutesPlayed, int rankAssistDividedMinutesPlayed,
			int shotAttempts, int rankShotAttempts, int shotOnTarget, int rankShotOnTarget,
			double shotOnTargetDividedShotAttempts, int shotOnTargetDividedShotAttemptsRank, int goals, int goalsRank,
			double goalsDividedShotAttempts, int goalsDividedShotAttemptsRank, double goalsDividedMinutesPlayed,
			int goalsDividedMinutesPlayedRank, int dribbles, int dribblesRanK, double dribblesDividedMinutesPlayed,
			int dribblesDividedMinutesPlayedRank, int minutesPlayed, int matchPlayed) {
		IDGiocatore = iDGiocatore;
		this.club = club;
		this.ballsRecoverd = ballsRecoverd;
		this.ballsRecoverdRank = ballsRecoverdRank;
		this.ballsRecoverdDividedMinutesPlayed = ballsRecoverdDividedMinutesPlayed;
		this.rankBallsRecoverdDividedMinutesPlayed = rankBallsRecoverdDividedMinutesPlayed;
		this.tackles = tackles;
		this.tacklesRank = tacklesRank;
		this.tacklesWon = tacklesWon;
		this.tacklesWonRank = tacklesWonRank;
		this.tacklesWonDividedTackles = tacklesWonDividedTackles;
		this.rankTacklesWonDividedTackles = rankTacklesWonDividedTackles;
		this.passAttempted = passAttempted;
		this.passAttemptedRank = passAttemptedRank;
		this.passCompleted = passCompleted;
		this.passCompletedRank = passCompletedRank;
		this.passCompletedDividedPassAttempted = passCompletedDividedPassAttempted;
		this.passCompletedDividedPassAttemptedRank = passCompletedDividedPassAttemptedRank;
		this.assist = assist;
		this.rankAssist = rankAssist;
		this.assistDividedMinutesPlayed = assistDividedMinutesPlayed;
		this.rankAssistDividedMinutesPlayed = rankAssistDividedMinutesPlayed;
		this.shotAttempts = shotAttempts;
		this.rankShotAttempts = rankShotAttempts;
		this.shotOnTarget = shotOnTarget;
		this.rankShotOnTarget = rankShotOnTarget;
		this.shotOnTargetDividedShotAttempts = shotOnTargetDividedShotAttempts;
		this.shotOnTargetDividedShotAttemptsRank = shotOnTargetDividedShotAttemptsRank;
		this.goals = goals;
		this.goalsRank = goalsRank;
		this.goalsDividedShotAttempts = goalsDividedShotAttempts;
		this.goalsDividedShotAttemptsRank = goalsDividedShotAttemptsRank;
		this.goalsDividedMinutesPlayed = goalsDividedMinutesPlayed;
		this.goalsDividedMinutesPlayedRank = goalsDividedMinutesPlayedRank;
		this.dribbles = dribbles;
		this.dribblesRanK = dribblesRanK;
		this.dribblesDividedMinutesPlayed = dribblesDividedMinutesPlayed;
		this.dribblesDividedMinutesPlayedRank = dribblesDividedMinutesPlayedRank;
		this.minutesPlayed = minutesPlayed;
		this.matchPlayed = matchPlayed;
	}
	
	/**
	 * Costruttore statistiche Squadra
	 * @param club
	 * @param saved
	 * @param savedRank
	 * @param savedDividedConcededPlusSaved
	 * @param ranksavedDividedConcededPlusSaved
	 * @param conceded
	 * @param concededRank
	 * @param concededDividedMatchPlayed
	 * @param rankConcededDividedMatchPlayed
	 * @param cleansheets
	 * @param cleansheetsRank
	 * @param ballsRecoverd
	 * @param ballsRecoverdRank
	 * @param ballsRecoverdDividedMatchPlayed
	 * @param rankBallsRecoverdDividedMatchPlayed
	 * @param tackles
	 * @param tacklesRank
	 * @param tacklesWon
	 * @param tacklesWonRank
	 * @param tacklesWonDividedTackles
	 * @param rankTacklesWonDividedTackles
	 * @param passAttempted
	 * @param passAttemptedRank
	 * @param passCompleted
	 * @param passCompletedRank
	 * @param passCompletedDividedPassAttempted
	 * @param passCompletedDividedPassAttemptedRank
	 * @param assist
	 * @param rankAssist
	 * @param assistDividedMatchPlayed
	 * @param rankAssistDividedMatchPlayed
	 * @param shotAttempts
	 * @param rankShotAttempts
	 * @param shotOnTarget
	 * @param rankShotOnTarget
	 * @param shotOnTargetDividedShotAttempts
	 * @param shotOnTargetDividedShotAttemptsRank
	 * @param goals
	 * @param goalsRank
	 * @param goalsDividedShotAttempts
	 * @param goalsDividedShotAttemptsRank
	 * @param goalsDividedMatchPlayed
	 * @param goalsDividedMatchPlayedRank
	 * @param dribbles
	 * @param dribblesRanK
	 * @param dribblesDividedMatchPlayed
	 * @param dribblesDividedMatchPlayedRank
	 * @param matchPlayed
	 */
	public Statistiche(String club, int saved, int savedRank, double savedDividedConcededPlusSaved,
			int ranksavedDividedConcededPlusSaved, int conceded, int concededRank, double concededDividedMatchPlayed,
			int rankConcededDividedMatchPlayed, int cleansheets, int cleansheetsRank, int ballsRecoverd,
			int ballsRecoverdRank, double ballsRecoverdDividedMatchPlayed, int rankBallsRecoverdDividedMatchPlayed,
			int tackles, int tacklesRank, int tacklesWon, int tacklesWonRank, double tacklesWonDividedTackles,
			int rankTacklesWonDividedTackles, int passAttempted, int passAttemptedRank, int passCompleted,
			int passCompletedRank, double passCompletedDividedPassAttempted, int passCompletedDividedPassAttemptedRank,
			int assist, int rankAssist, double assistDividedMatchPlayed, int rankAssistDividedMatchPlayed,
			int shotAttempts, int rankShotAttempts, int shotOnTarget, int rankShotOnTarget,
			double shotOnTargetDividedShotAttempts, int shotOnTargetDividedShotAttemptsRank, int goals, int goalsRank,
			double goalsDividedShotAttempts, int goalsDividedShotAttemptsRank, double goalsDividedMatchPlayed,
			int goalsDividedMatchPlayedRank, int dribbles, int dribblesRanK, double dribblesDividedMatchPlayed,
			int dribblesDividedMatchPlayedRank, int matchPlayed) {
		this.club = club;
		this.saved = saved;
		this.savedRank = savedRank;
		this.savedDividedConcededPlusSaved = savedDividedConcededPlusSaved;
		this.ranksavedDividedConcededPlusSaved = ranksavedDividedConcededPlusSaved;
		this.conceded = conceded;
		this.concededRank = concededRank;
		this.concededDividedMatchPlayed = concededDividedMatchPlayed;
		this.rankConcededDividedMatchPlayed = rankConcededDividedMatchPlayed;
		this.cleansheets = cleansheets;
		this.cleansheetsRank = cleansheetsRank;
		this.ballsRecoverd = ballsRecoverd;
		this.ballsRecoverdRank = ballsRecoverdRank;
		this.ballsRecoverdDividedMatchPlayed = ballsRecoverdDividedMatchPlayed;
		this.rankBallsRecoverdDividedMatchPlayed = rankBallsRecoverdDividedMatchPlayed;
		this.tackles = tackles;
		this.tacklesRank = tacklesRank;
		this.tacklesWon = tacklesWon;
		this.tacklesWonRank = tacklesWonRank;
		this.tacklesWonDividedTackles = tacklesWonDividedTackles;
		this.rankTacklesWonDividedTackles = rankTacklesWonDividedTackles;
		this.passAttempted = passAttempted;
		this.passAttemptedRank = passAttemptedRank;
		this.passCompleted = passCompleted;
		this.passCompletedRank = passCompletedRank;
		this.passCompletedDividedPassAttempted = passCompletedDividedPassAttempted;
		this.passCompletedDividedPassAttemptedRank = passCompletedDividedPassAttemptedRank;
		this.assist = assist;
		this.rankAssist = rankAssist;
		this.assistDividedMatchPlayed = assistDividedMatchPlayed;
		this.rankAssistDividedMatchPlayed = rankAssistDividedMatchPlayed;
		this.shotAttempts = shotAttempts;
		this.rankShotAttempts = rankShotAttempts;
		this.shotOnTarget = shotOnTarget;
		this.rankShotOnTarget = rankShotOnTarget;
		this.shotOnTargetDividedShotAttempts = shotOnTargetDividedShotAttempts;
		this.shotOnTargetDividedShotAttemptsRank = shotOnTargetDividedShotAttemptsRank;
		this.goals = goals;
		this.goalsRank = goalsRank;
		this.goalsDividedShotAttempts = goalsDividedShotAttempts;
		this.goalsDividedShotAttemptsRank = goalsDividedShotAttemptsRank;
		this.goalsDividedMatchPlayed = goalsDividedMatchPlayed;
		this.goalsDividedMatchPlayedRank = goalsDividedMatchPlayedRank;
		this.dribbles = dribbles;
		this.dribblesRanK = dribblesRanK;
		this.dribblesDividedMatchPlayed = dribblesDividedMatchPlayed;
		this.dribblesDividedMatchPlayedRank = dribblesDividedMatchPlayedRank;
		this.matchPlayed = matchPlayed;
	}
	
	public int getIDGiocatore() {
		return IDGiocatore;
	}

	public String getClub() {
		return club;
	}

	public int getSaved() {
		return saved;
	}

	public double getSavedDividedConcededPlusSaved() {
		return savedDividedConcededPlusSaved;
	}

	public int getConceded() {
		return conceded;
	}

	public double getConcededDividedMinutesPlayed() {
		return concededDividedMinutesPlayed;
	}

	public double getConcededDividedMatchPlayed() {
		return concededDividedMatchPlayed;
	}

	public int getCleansheets() {
		return cleansheets;
	}

	public int getBallsRecoverd() {
		return ballsRecoverd;
	}

	public double getBallsRecoverdDividedMinutesPlayed() {
		return ballsRecoverdDividedMinutesPlayed;
	}

	public double getBallsRecoverdDividedMatchPlayed() {
		return ballsRecoverdDividedMatchPlayed;
	}

	public int getTackles() {
		return tackles;
	}

	public int getTacklesWon() {
		return tacklesWon;
	}

	public double getTacklesWonDividedTackles() {
		return tacklesWonDividedTackles;
	}

	public int getPassAttempted() {
		return passAttempted;
	}

	public int getPassCompleted() {
		return passCompleted;
	}

	public double getPassCompletedDividedPassAttempted() {
		return passCompletedDividedPassAttempted;
	}

	public int getAssist() {
		return assist;
	}

	public double getAssistDividedMinutesPlayed() {
		return assistDividedMinutesPlayed;
	}

	public double getAssistDividedMatchPlayed() {
		return assistDividedMatchPlayed;
	}

	public int getShotAttempts() {
		return shotAttempts;
	}

	public int getShotOnTarget() {
		return shotOnTarget;
	}

	public double getShotOnTargetDividedShotAttempts() {
		return shotOnTargetDividedShotAttempts;
	}

	public int getGoals() {
		return goals;
	}

	public double getGoalsDividedShotAttempts() {
		return goalsDividedShotAttempts;
	}

	public double getGoalsDividedMinutesPlayed() {
		return goalsDividedMinutesPlayed;
	}

	public double getGoalsDividedMatchPlayed() {
		return goalsDividedMatchPlayed;
	}

	public int getDribbles() {
		return dribbles;
	}

	public double getDribblesDividedMinutesPlayed() {
		return dribblesDividedMinutesPlayed;
	}

	public double getDribblesDividedMatchPlayed() {
		return dribblesDividedMatchPlayed;
	}

	public int getMinutesPlayed() {
		return minutesPlayed;
	}

	public int getMatchPlayed() {
		return matchPlayed;
	}

	public int getSavedRank() {
		return savedRank;
	}

	public int getRanksavedDividedConcededPlusSaved() {
		return ranksavedDividedConcededPlusSaved;
	}

	public int getConcededRank() {
		return concededRank;
	}

	public int getRankConcededDividedMinutesPlayed() {
		return rankConcededDividedMinutesPlayed;
	}

	public int getRankConcededDividedMatchPlayed() {
		return rankConcededDividedMatchPlayed;
	}

	public int getCleansheetsRank() {
		return cleansheetsRank;
	}

	public int getBallsRecoverdRank() {
		return ballsRecoverdRank;
	}

	public int getRankBallsRecoverdDividedMinutesPlayed() {
		return rankBallsRecoverdDividedMinutesPlayed;
	}

	public int getRankBallsRecoverdDividedMatchPlayed() {
		return rankBallsRecoverdDividedMatchPlayed;
	}

	public int getTacklesRank() {
		return tacklesRank;
	}

	public int getTacklesWonRank() {
		return tacklesWonRank;
	}

	public int getRankTacklesWonDividedTackles() {
		return rankTacklesWonDividedTackles;
	}

	public int getPassAttemptedRank() {
		return passAttemptedRank;
	}

	public int getPassCompletedRank() {
		return passCompletedRank;
	}

	public int getPassCompletedDividedPassAttemptedRank() {
		return passCompletedDividedPassAttemptedRank;
	}

	public int getRankAssist() {
		return rankAssist;
	}

	public int getRankAssistDividedMinutesPlayed() {
		return rankAssistDividedMinutesPlayed;
	}

	public int getRankAssistDividedMatchPlayed() {
		return rankAssistDividedMatchPlayed;
	}

	public int getRankShotAttempts() {
		return rankShotAttempts;
	}

	public int getRankShotOnTarget() {
		return rankShotOnTarget;
	}

	public int getShotOnTargetDividedShotAttemptsRank() {
		return shotOnTargetDividedShotAttemptsRank;
	}

	public int getGoalsRank() {
		return goalsRank;
	}

	public int getGoalsDividedShotAttemptsRank() {
		return goalsDividedShotAttemptsRank;
	}

	public int getGoalsDividedMinutesPlayedRank() {
		return goalsDividedMinutesPlayedRank;
	}

	public int getGoalsDividedMatchPlayedRank() {
		return goalsDividedMatchPlayedRank;
	}

	public int getDribblesRanK() {
		return dribblesRanK;
	}

	public int getDribblesDividedMinutesPlayedRank() {
		return dribblesDividedMinutesPlayedRank;
	}

	public int getDribblesDividedMatchPlayedRank() {
		return dribblesDividedMatchPlayedRank;
	}

	@Override
	public int hashCode() {
		return Objects.hash(IDGiocatore, club);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statistiche other = (Statistiche) obj;
		return IDGiocatore == other.IDGiocatore && Objects.equals(club, other.club);
	}

	@Override
	public String toString() {
		return this.club+",  "+this.getMatchPlayed()+"  partite giocate.\n\nSTATISTICHE PORTIERI:\n"+
				"- saved: "+this.getSaved()+" → "+this.getSavedRank()+"º\n"+
				"- saved / (conceded + saved): "+ this.getSavedDividedConcededPlusSaved()+" → "+this.getRanksavedDividedConcededPlusSaved()+"º\n"+
				"- conceded: "+this.getConceded()+" → "+this.getConcededRank()+"º\n"+
				"- conceded / match played: "+this.getConcededDividedMatchPlayed()+" → "+this.getRankConcededDividedMatchPlayed()+"º\n"+
				"- cleansheets: "+this.getCleansheets()+ " → " + this.getCleansheetsRank()+"º\n\nSTATISTICHE DIFENSIVE:\n"+
				"- balls recoverd: "+this.getBallsRecoverd()+" → "+this.getBallsRecoverdRank()+"º\n"+
				"- balls recoverd / match played: "+ this.getBallsRecoverdDividedMatchPlayed()+" → "+this.getRankBallsRecoverdDividedMatchPlayed()+"º\n"+
				"- tackles: "+this.getTackles()+" → "+this.getTacklesRank()+"º\n"+
				"- tackles won: "+this.getTacklesWon()+" → "+this.getTacklesWonRank()+"º\n"+
				"- tackles won / tackles: "+this.getTacklesWonDividedTackles()+" → "+this.getRankTacklesWonDividedTackles()+"º\n\nSTATISTICHE PASSAGGI:\n"+
				"- pass attempted: "+this.getPassAttempted()+" → "+this.getPassAttemptedRank()+"º\n"+
				"- pass completed: "+this.getPassCompleted()+" → "+this.getPassCompletedRank()+"º\n"+
				"- pass completed / pass attempted: "+this.getPassCompletedDividedPassAttempted()+" → "+this.getPassCompletedDividedPassAttemptedRank()+"º\n"+
				"- assist: "+this.getAssist()+" → "+this.getRankAssist()+"º\n"+
				"- assist / match played: "+this.getAssistDividedMatchPlayed()+" → "+this.getRankAssistDividedMatchPlayed()+"º\n\nSTATISTICHE OFFENSIVE:\n"+
				"- shot attempts: "+this.getShotAttempts()+" → "+this.getRankShotAttempts()+"º\n"+
				"- shot on target: "+this.getShotOnTarget()+" → "+this.getRankShotOnTarget()+"º\n"+
				"- shot on target / shot attempts: "+this.getShotOnTargetDividedShotAttempts()+" → "+this.getShotOnTargetDividedShotAttemptsRank()+"º\n"+
				"- goals: "+this.getGoals()+" → "+this.getGoalsRank()+"º\n"+
				"- goals / shot attempts: "+this.getGoalsDividedShotAttempts()+" → "+this.getGoalsDividedShotAttemptsRank()+"º\n"+
				"- goals / match played: "+this.getGoalsDividedMatchPlayed()+" → "+this.getGoalsDividedMatchPlayedRank()+"º\n"+
				"- dribbles: "+this.getDribbles()+" → "+this.getDribblesRanK()+"º\n"+					
				"- dribbles / match played: "+this.getDribblesDividedMatchPlayed()+ " → " + this.getDribblesDividedMatchPlayedRank()+"º";
	}

	
	
}
