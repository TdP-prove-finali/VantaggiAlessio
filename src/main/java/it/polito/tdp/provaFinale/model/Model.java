package it.polito.tdp.provaFinale.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.provaFinale.dao.ChampionsDAO;

public class Model {
	
	private ChampionsDAO dao;
	
	// Parametri RICORSIONE
	
	private List<DreamTeam> bestPlayers;
	private List<DreamTeam> playersForDreamTeam;
	
	// Ottimizzazione legata ai ruoli dei giocatori
	private int spyNumDif;
	private int spyNumCen;
	private int spyNumAtt;
	
	
	// Ottimizzazione legata a value e wage
	private double maxTeamValue;
	private double maxTeamWage;
	
	// Ottimizzazione legata a giocatori appartenenti alla stessa squadra 
	private int playersFromSameTeam;
	
	// Valore 
	private double bestStatsValue;
	
	
	public Model() {
		this.dao= new ChampionsDAO();
		
	}
	
	public List<Giocatore> getAllPlayers(){
		return this.dao.getAllPlayers();
	}
	
	public List<Squadra> getAllTeams(){
		return this.dao.getAllTeams();
	}
	
	// Creazione Dream Team senza vincoli
	public String createDreamTeam(String formation, String goalkeeperStats, String playersStats){
		String[] reparti= formation.split(" - ");
		
		String risultato="DREAM TEAM senza vincoli:\n\nPORTIERE\n";
		risultato+= ""+getGoalkeeperDreamTeam(goalkeeperStats)+"\n\nDIFENSORI\n";
		
		List<DreamTeam> lista= getPlayersDreamTeam(playersStats, Integer.parseInt(reparti[0]), Integer.parseInt(reparti[1]), Integer.parseInt(reparti[2]));
		
		for(DreamTeam player: lista.subList(0, Integer.parseInt(reparti[0])))
			risultato+= ""+player+"\n";
		
		risultato+="\nCENTROCAMPISTI\n";
		
		for(DreamTeam player: lista.subList(Integer.parseInt(reparti[0]), Integer.parseInt(reparti[1]) + Integer.parseInt(reparti[0])))
			risultato+= ""+player+"\n";
		
		risultato+= "\nATTACCANTI\n";
		
		for(DreamTeam player: lista.subList(Integer.parseInt(reparti[1]) + Integer.parseInt(reparti[0]), lista.size()))
			risultato+= ""+player+"\n";
			
		return risultato.substring(0, risultato.length());		
	}
	
	/* Portiere */
	private DreamTeam getGoalkeeperDreamTeam(String stats){
		DreamTeam risultato= null;
		
		if(stats.equals("saved / (conceded + saved)"))
			risultato= this.dao.goalkeeperForSavedDividedConcededPlusSaved();
		
		else if(stats.equals("conceded / minutes played"))
			risultato= this.dao.goalkeeperForConcededDividedMinutesPlayed();
		
		else if(stats.equals("clean sheets / match played"))
			risultato= this.dao.goalkeeperForCleansheetsDividedMatchPlayed();		
		
		return risultato;	
	}
	
	/* Giocatori di movimento */
	private List<DreamTeam> getPlayersDreamTeam(String stats, int numDifensori, int numCentrocampisti, int numAttaccanti){
		List<DreamTeam> parziale= new ArrayList<>();
		List<DreamTeam> risultato= new ArrayList<>();
		
		int difCount= 0;
		int cenCount= 0;
		int attCount= 0;
		
		if(stats.equals("balls recoverd / minutes played"))
			parziale= this.dao.playersForBallsRecoverdDividedMinutesPlayed();
		
		else if(stats.equals("tackles won / tackles"))
			parziale= this.dao.playersForTacklesWonDividedTackles();
				
		else if(stats.equals("pass completed / pass attempted"))
			parziale= this.dao.playersForPassCompletedDividedPassAttempted();
	
		else if(stats.equals("assist / minutes played"))
			parziale= this.dao.playersForAssistDividedMinutesPlayed();
			
		else if(stats.equals("goals / minutes played"))
			parziale= this.dao.playersForGoalsDividedMinutesPlayed();
			
		else if(stats.equals("dribbles / minutes played"))
			parziale= this.dao.playersForDribblesDividedMinutesPlayed();
			
		else if(stats.equals("shot on target / shot attempts"))
			parziale= this.dao.playersForShotOnTargetDividedShotAttempts();
			
		else if(stats.equals("goals / shot attempts"))
			parziale= this.dao.playersForGoalsDividedShotAttempts();

		for(DreamTeam player: parziale) {
			if(difCount==numDifensori && cenCount==numCentrocampisti && attCount==numAttaccanti)
				break;
			
			else {
				if(player.getPosition().equals("Difensore") && difCount< numDifensori) {
					difCount++;
					risultato.add(player);
				}
					
				else if (player.getPosition().equals("Centrocampista") && cenCount< numCentrocampisti) {
					cenCount++;
					risultato.add(player);
				}
					
				else if (player.getPosition().equals("Attaccante") && attCount< numAttaccanti) {
					attCount++;
					risultato.add(player);
				}						
			}
		}
		
		Collections.sort(risultato);
		return risultato;
	}
	
	
	// Creazione Dream Team con vincoli
	public String createDreamTeam(String formation, String goalkeeperStats, String playersStats,
			double value, double wage,double goalkeeperValue, double goalkeeperWage,
			int minutesPlayed,int minAge, int maxAge, int maxSameTeam){
		
		String[] reparti= formation.split(" - ");
		
		// Verifico i vincoli del portiere 
		DreamTeam portiere= getGoalkeeperDreamTeam(goalkeeperStats, minutesPlayed, minAge, maxAge, goalkeeperWage, goalkeeperValue);
		if(portiere== null)
			return "Errore creazione DREAM TEAM: nessun portiere selezionabile con vincoli inseriti!";
		
		double teamValue= value-portiere.getFifaValue();
		double teamWage= wage-portiere.getFifaWage();
		
		// Verifico i vincoli dei giocatori 
		List<DreamTeam> lista= getPlayersDreamTeam(playersStats, Integer.parseInt(reparti[0]), Integer.parseInt(reparti[1]), Integer.parseInt(reparti[2]),
				minutesPlayed, minAge, maxAge, teamWage, teamValue, maxSameTeam);
				
		if(lista== null)
			return "Errore creazione DREAM TEAM: vincoli giocatori di movimento troppo stringenti!";
		
		if(lista.isEmpty())
			return "Errore creazione DREAM TEAM: vincoli giocatori di movimento troppo laschi, algoritmo computazionalmente troppo oneroso!";
		
		// Se sono qui sono sicuro che ho un dream team 
		String risultato="DREAM TEAM con vincoli:\n\nPORTIERE\n"+portiere+"\n\nDIFENSORI\n";
				
		for(DreamTeam player: lista.subList(0, Integer.parseInt(reparti[0])))
			risultato+= ""+player+"\n";
		
		risultato+="\nCENTROCAMPISTI\n";
		
		for(DreamTeam player: lista.subList(Integer.parseInt(reparti[0]), Integer.parseInt(reparti[1]) + Integer.parseInt(reparti[0])))
			risultato+= ""+player+"\n";
		
		risultato+= "\nATTACCANTI\n";
		
		for(DreamTeam player: lista.subList(Integer.parseInt(reparti[1]) + Integer.parseInt(reparti[0]), lista.size()))
			risultato+= ""+player+"\n";
			
		return risultato.substring(0, risultato.length());
	}
	
	/* Portiere */
	private DreamTeam getGoalkeeperDreamTeam(String stats,int minMinutesPlayed, int minAge, int maxAge, double maxWage, double maxValue){
		DreamTeam risultato= null;
		
		if(stats.equals("saved / (conceded + saved)"))
			risultato= this.dao.goalkeeperForSavedDividedConcededPlusSaved(minMinutesPlayed, minAge, maxAge, maxWage, maxValue);
		
		else if(stats.equals("conceded / minutes played"))
			risultato= this.dao.goalkeeperForConcededDividedMinutesPlayed(minMinutesPlayed, minAge, maxAge, maxWage, maxValue);
		
		else if(stats.equals("clean sheets / match played"))
			risultato= this.dao.goalkeeperForCleansheetsDividedMatchPlayed(minMinutesPlayed, minAge, maxAge, maxWage, maxValue);		
		
		return risultato;	
	}
	
	/* Giocatori di movimento */
	private List<DreamTeam> getPlayersDreamTeam(String stats, int numDifensori, int numCentrocampisti, int numAttaccanti,
			int minMinutesPlayed, int minAge, int maxAge, double maxWage, double maxValue, int maxSameTeam){
		
		List<DreamTeam> lista= new ArrayList<>();
		
		if(stats.equals("balls recoverd / minutes played"))
			lista= this.dao.playersForBallsRecoverdDividedMinutesPlayed(minMinutesPlayed, minAge, maxAge, maxWage, maxValue);
		
		else if(stats.equals("tackles won / tackles"))
			lista= this.dao.playersForTacklesWonDividedTackles(minMinutesPlayed, minAge, maxAge, maxWage, maxValue);
				
		else if(stats.equals("pass completed / pass attempted"))
			lista= this.dao.playersForPassCompletedDividedPassAttempted(minMinutesPlayed, minAge, maxAge, maxWage, maxValue);
	
		else if(stats.equals("assist / minutes played"))
			lista= this.dao.playersForAssistDividedMinutesPlayed(minMinutesPlayed, minAge, maxAge, maxWage, maxValue);
			
		else if(stats.equals("goals / minutes played"))
			lista= this.dao.playersForGoalsDividedMinutesPlayed(minMinutesPlayed, minAge, maxAge, maxWage, maxValue);
			
		else if(stats.equals("dribbles / minutes played"))
			lista= this.dao.playersForDribblesDividedMinutesPlayed(minMinutesPlayed, minAge, maxAge, maxWage, maxValue);
			
		else if(stats.equals("shot on target / shot attempts"))
			lista= this.dao.playersForShotOnTargetDividedShotAttempts(minMinutesPlayed, minAge, maxAge, maxWage, maxValue);
			
		else if(stats.equals("goals / shot attempts"))
			lista= this.dao.playersForGoalsDividedShotAttempts(minMinutesPlayed, minAge, maxAge, maxWage, maxValue);
		
		// Se ho meno di dieci elementi nella lista non posso formare un team
		if(lista.size()<10)
			return null;
		
		// Ho almeno 10 elementi nella lista
		List<DreamTeam> risultatoNoVincoli= new ArrayList<>();
		int difCount= 0;
		int cenCount= 0;
		int attCount= 0;
		
		double sumValue=0.0;
		double sumWage=0.0;
		Map<String, Integer> mapMaxPlayersSameTeam= new HashMap<>();
		
		for(DreamTeam player: lista) {
			if(difCount==numDifensori && cenCount==numCentrocampisti && attCount==numAttaccanti)
				break;
			
			else {
				if(player.getPosition().equals("Difensore") && difCount< numDifensori) {
					difCount++;
					sumValue+= player.getFifaValue();
					sumWage+= player.getFifaWage();
					
					if(mapMaxPlayersSameTeam.putIfAbsent(player.getClub(), 1)!= null)
						mapMaxPlayersSameTeam.put(player.getClub(), mapMaxPlayersSameTeam.get(player.getClub())+1);
					
					risultatoNoVincoli.add(player);
				}
					
				else if (player.getPosition().equals("Centrocampista") && cenCount< numCentrocampisti) {
					cenCount++;
					sumValue+= player.getFifaValue();
					sumWage+= player.getFifaWage();
					
					if(mapMaxPlayersSameTeam.putIfAbsent(player.getClub(), 1)!= null)
						mapMaxPlayersSameTeam.put(player.getClub(), mapMaxPlayersSameTeam.get(player.getClub())+1);
					
					risultatoNoVincoli.add(player);
				}
					
				else if (player.getPosition().equals("Attaccante") && attCount< numAttaccanti) {
					attCount++;
					sumValue+= player.getFifaValue();
					sumWage+= player.getFifaWage();
					
					if(mapMaxPlayersSameTeam.putIfAbsent(player.getClub(), 1)!= null)
						mapMaxPlayersSameTeam.put(player.getClub(), mapMaxPlayersSameTeam.get(player.getClub())+1);
					
					risultatoNoVincoli.add(player);
				}						
			}
		}
		
		// Ho piu di 10 giocatori, ma non soddisfano i requisiti dei ruoli
		if(difCount< numDifensori || cenCount< numCentrocampisti || attCount< numAttaccanti)
			return null;
		
		// Se sono qui i giocatori soddisfano tutti i vincoli, devo solo trovare la soluzione ottima
		
		boolean spy= true;
		for(int i: mapMaxPlayersSameTeam.values()) {
			if(i> maxSameTeam) {
				spy=false;
				break;
			}
		}
		
		// Controllo se la lista senza vincoli può essere la soluzione ottima
		if(spy && difCount== numDifensori && cenCount== numCentrocampisti && attCount== numAttaccanti && sumValue<= maxValue && sumWage<= maxWage) {
			Collections.sort(risultatoNoVincoli);
			return risultatoNoVincoli;
		}
		
		// Verifico se i vincoli imposti dall'utente siano troppo onerosi
		if(maxSameTeam== 3 && lista.size()> 42) 
			return new ArrayList<>();
		if(maxSameTeam== 2 && lista.size()> 44) 
			return new ArrayList<>();
		if(maxSameTeam== 1 && lista.size()> 52) 
			return new ArrayList<>();
		
				
		// Se sono qui la lista sopra non soddisfa i vincoli -> RICORSIONE
		this.bestPlayers= new ArrayList<>();
		this.playersForDreamTeam= new ArrayList<>(lista);
		System.out.println(this.playersForDreamTeam.size());
		List<DreamTeam> parziale= new ArrayList<>();
		
		// Popolo la mappa con tutte le squadre e numero di giocatori uguale a 0
		Map<String, Integer> clubs= new HashMap<>();
		for(Squadra s: getAllTeams())
			clubs.put(s.getClub(), 0);
		
		this.spyNumDif=0;
		this.spyNumCen=0;
		this.spyNumAtt=0;		
		
		this.playersFromSameTeam= maxSameTeam;
		
		this.bestStatsValue= 0.0;
		this.maxTeamValue= maxValue;
		this.maxTeamWage= maxWage;
		
		ricorsione(parziale, 0, 0.0, 0.0, 0.0, clubs, numDifensori, numCentrocampisti, numAttaccanti);
	
		if(! this.bestPlayers.isEmpty()) {
			Collections.sort(this.bestPlayers);
			return this.bestPlayers;
		}
			
		
		return null;
	}
	
	private void ricorsione(List<DreamTeam> parziale, int livello, double statistica,double value, double wage, Map<String, Integer> clubs, 
			int Ndifensori, int Ncentrocampisti, int Nattaccanti) {
		
		if(livello== 10) {	
			if(statistica> this.bestStatsValue) {
				this.bestPlayers= new ArrayList<>(parziale);
				this.bestStatsValue= statistica;
			}
			return;
		}
		
		for(DreamTeam player: this.playersForDreamTeam) {
			String position= player.getPosition();
			
			if(checkPosition(position, Ndifensori, Ncentrocampisti, Nattaccanti) &&
					(livello==0 || parziale.get(parziale.size()-1).getName().compareTo(player.getName())< 0) && 
					((value+player.getFifaValue())<= this.maxTeamValue) && ((wage+player.getFifaWage())<= this.maxTeamWage) &&
					clubs.get(player.getClub())< this.playersFromSameTeam) {
				
				parziale.add(player);
				clubs.put(player.getClub(), clubs.get(player.getClub())+1);
				// Aggiornare il numero di giocatori per quel ruolo 
				editFormation(position, 1);
				
				
				ricorsione(parziale, livello+1, statistica+player.getStatsValue(), value+player.getFifaValue(), wage+player.getFifaWage(), clubs,
						Ndifensori, Ncentrocampisti, Nattaccanti);
				
				clubs.put(player.getClub(), clubs.get(player.getClub())-1);
				editFormation(position, -1);
				parziale.remove(parziale.size()-1);
			}

		}
		return;				
	}
	
	private boolean checkPosition(String position, int difensori, int centrocampisti, int attaccanti) {
		if(position.equals("Difensore") && this.spyNumDif< difensori)
			return true;
		if(position.equals("Centrocampista") && this.spyNumCen< centrocampisti)
			return true;
		if(position.equals("Attaccante") && this.spyNumAtt< attaccanti)
			return true;
		
		return false;
	}
	
	private void editFormation(String position, int i) {
		if(position.equals("Difensore")) {
			this.spyNumDif= this.spyNumDif + i;
			return;
		}
			
		if(position.equals("Centrocampista")) {
			this.spyNumCen= this.spyNumCen + i;
			return;
		}
			
		if(position.equals("Attaccante")) {
			this.spyNumAtt= this.spyNumAtt+ i;
			return;
		}			
		
		return;
	}

	// Punto 1 -> Statistiche singolo giocatore
	public String getStatisticsPlayer(Giocatore player) {
		String risultato="In totale nel database ci sono: "+this.getAllPlayers().size()+" giocatori.\n\nGIOCATORE SELEZIONATO:\n";
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		
		if(player.getPosition().equals("Portiere")) {
			Statistiche stats= this.dao.getStatisticsGoalkeeper(player.getIDGiocatore());
			risultato+= player.getName()+",  "+player.getPosition()+",  "+stats.getClub()+",  "+player.getAge()+"  anni,  FIFA value: "+
					decimalFormat.format(player.getFifaValue())+" €,  FIFA wage: "+decimalFormat.format(player.getFifaWage())+" €\n\nSTATISTICHE PORTIERE:\n"+
					"- saved: "+stats.getSaved()+" → "+stats.getSavedRank()+"º\n"+
					"- saved / (conceded + saved): "+ stats.getSavedDividedConcededPlusSaved()+" → "+stats.getRanksavedDividedConcededPlusSaved()+"º\n"+
					"- conceded: "+stats.getConceded()+" → "+stats.getConcededRank()+"º\n"+
					"- conceded / minutes played: "+stats.getConcededDividedMinutesPlayed()+" → "+stats.getRankConcededDividedMinutesPlayed()+"º\n"+
					"- cleansheets: "+stats.getCleansheets()+ " → " + stats.getCleansheetsRank()+"º\n\n"+
					"Ha giocato: "+stats.getMinutesPlayed()+" minuti,  "+stats.getMatchPlayed()+"  partite.";		
		}
		
		else {
			Statistiche stats= this.dao.getStatisticsPlayer(player.getIDGiocatore());
			risultato+= player.getName()+",  "+player.getPosition()+",  "+stats.getClub()+",  "+player.getAge()+"  anni,  FIFA value: "+
					decimalFormat.format(player.getFifaValue())+" €,  FIFA wage: "+decimalFormat.format(player.getFifaWage())+" €\n\nSTATISTICHE DIFENSIVE:\n"+
					"- balls recoverd: "+stats.getBallsRecoverd()+" → "+stats.getBallsRecoverdRank()+"º\n"+
					"- balls recoverd / minutes played: "+ stats.getBallsRecoverdDividedMinutesPlayed()+" → "+stats.getRankBallsRecoverdDividedMinutesPlayed()+"º\n"+
					"- tackles: "+stats.getTackles()+" → "+stats.getTacklesRank()+"º\n"+
					"- tackles won: "+stats.getTacklesWon()+" → "+stats.getTacklesWonRank()+"º\n"+
					"- tackles won / tackles: "+stats.getTacklesWonDividedTackles()+" → "+stats.getRankTacklesWonDividedTackles()+"º\n\nSTATISTICHE PASSAGGI:\n"+
					"- pass attempted: "+stats.getPassAttempted()+" → "+stats.getPassAttemptedRank()+"º\n"+
					"- pass completed: "+stats.getPassCompleted()+" → "+stats.getPassCompletedRank()+"º\n"+
					"- pass completed / pass attempted: "+stats.getPassCompletedDividedPassAttempted()+" → "+stats.getPassCompletedDividedPassAttemptedRank()+"º\n"+
					"- assist: "+stats.getAssist()+" → "+stats.getRankAssist()+"º\n"+
					"- assist / minutes played: "+stats.getAssistDividedMinutesPlayed()+" → "+stats.getRankAssistDividedMinutesPlayed()+"º\n\nSTATISTICHE OFFENSIVE:\n"+
					"- shot attempts: "+stats.getShotAttempts()+" → "+stats.getRankShotAttempts()+"º\n"+
					"- shot on target: "+stats.getShotOnTarget()+" → "+stats.getRankShotOnTarget()+"º\n"+
					"- shot on target / shot attempts: "+stats.getShotOnTargetDividedShotAttempts()+" → "+stats.getShotOnTargetDividedShotAttemptsRank()+"º\n"+
					"- goals: "+stats.getGoals()+" → "+stats.getGoalsRank()+"º\n"+
					"- goals / shot attempts: "+stats.getGoalsDividedShotAttempts()+" → "+stats.getGoalsDividedShotAttemptsRank()+"º\n"+
					"- goals / minutes played: "+stats.getGoalsDividedMinutesPlayed()+" → "+stats.getGoalsDividedMinutesPlayedRank()+"º\n"+
					"- dribbles: "+stats.getDribbles()+" → "+stats.getDribblesRanK()+"º\n"+					
					"- dribbles / minutes played: "+stats.getDribblesDividedMinutesPlayed()+ " → " + stats.getDribblesDividedMinutesPlayedRank()+"º\n\n"+
					"Ha giocato: "+stats.getMinutesPlayed()+" minuti in "+stats.getMatchPlayed()+" partite.";
		}
		return risultato;
	}
	
	// Punto 2 -> Statistiche squadra
	public String getStatisticsClub(String team) {
		return "In totale nel database ci sono: "+this.getAllTeams().size()+" squadre.\n\nSQUADRA SELEZIONATA:\n"+this.dao.getStatisticsClub(team);
	}
}
