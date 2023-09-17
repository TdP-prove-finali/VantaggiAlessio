package it.polito.tdp.provaFinale;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.provaFinale.model.Giocatore;
import it.polito.tdp.provaFinale.model.Model;
import it.polito.tdp.provaFinale.model.Squadra;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClubStats;

    @FXML
    private Button btnDreamTeamConstraints;

    @FXML
    private Button btnDreamTeamNoConstraints;

    @FXML
    private Button btnPlayerStats;

    @FXML
    private ComboBox<Squadra> cmbClub;

    @FXML
    private ComboBox<String> cmbFormation;

    @FXML
    private ComboBox<String> cmbGoalkeeperStats;

    @FXML
    private ComboBox<Integer> cmbMaxAge;

    @FXML
    private ComboBox<Integer> cmbMinAge;

    @FXML
    private ComboBox<Giocatore> cmbPlayer;

    @FXML
    private ComboBox<Integer> cmbPlayersSameTeam;

    @FXML
    private ComboBox<String> cmbTeamStats;

    @FXML
    private TextField txtGoalkeeperSalary;

    @FXML
    private TextField txtGoalkeeperValue;

    @FXML
    private TextField txtMaxSalary;

    @FXML
    private TextField txtMaxValue;

    @FXML
    private TextField txtMinMinutesPlayed;

    @FXML
    private TextArea txtResult;

    @FXML
    void createDreamTeamWithConstraints(ActionEvent event) {  	
    	long startTime = System.currentTimeMillis();
    	double value= 0.0;
    	double wage= 0.0;
    	
    	double percentualePorValue= 0.0;
    	double percentualePorWage= 0.0;
    	
    	int minMinutesPlayed=0;
    	
    	if(! txtResult.getText().equals("")) {
    		if(! txtResult.getText().startsWith("E"))
    			txtResult.setText("");
    	}
    	
    	// Controllo campo non vuoto
    	if(txtMaxValue.getText().equals("")) {
    		txtResult.appendText("Errore in Budget totale value: inserire un valore numerico nel campo\n");
    		return;
    	}
    	if(txtMaxSalary.getText().equals("")) {
    		txtResult.appendText("Errore in Budget totale wage: inserire un valore numerico nel campo\n");
    		return;
    	}
    	if(txtGoalkeeperValue.getText().equals("")) {
    		txtResult.appendText("Errore in Percentuale value per il portiere: inserire un valore numerico nel campo\n");
    		return;
    	}
    	if(txtGoalkeeperSalary.getText().equals("")) {
    		txtResult.appendText("Errore in Percentuale wage per il portiere: inserire un valore numerico nel campo\n");
    		return;
    	}
    	if(txtMinMinutesPlayed.getText().equals("")) {
    		txtResult.appendText("Errore in Numero minimo minuti giocati: inserire un valore numerico nel campo\n");
    		return;
    	}
    	
    	// Controllo conversione
    	try {
    		value= Double.parseDouble(txtMaxValue.getText());
    		wage= Double.parseDouble(txtMaxSalary.getText());
    		
    		percentualePorValue= Double.parseDouble(txtGoalkeeperValue.getText())/100.00;
			percentualePorWage=Double.parseDouble(txtGoalkeeperSalary.getText())/100.00;
			
			minMinutesPlayed= Integer.parseInt(txtMinMinutesPlayed.getText());
			
		} catch (Exception e) {
			txtResult.appendText("Errore inserire valori numerici nei campi: Budget totale value, Percentuale value per il portiere, "
					+ "Budget totale wage, Percentuale wage per il portiere, "
					+ "Numero minimo minuti giocati!\n");
			return;
		}
    	
    	// Controllo formazione
    	if(cmbFormation.getValue()== null) {
    		txtResult.appendText("Errore in Modulo: selezionare un valore dal menù a tendina\n");
    		return;
    	}
    	
    	//Controllo percentuale 
    	if(percentualePorValue<0 || percentualePorValue> 100) {
    		txtResult.appendText("Errore in Percentuale valore economico da dedicare al portiere: inserire un valore percentuale compreso tra 0 e 100\n");
    		return;
    	}
    	if(percentualePorWage<0 || percentualePorWage> 100) {
    		txtResult.appendText("Errore in Percentuale salario da dedicare al portiere: inserire un valore percentuale compreso tra 0 e 100\n");
    		return;
    	}
    	
    	// Controllo caratteristiche 
    	if(cmbGoalkeeperStats.getValue()== null) {
    		txtResult.appendText("Errore in Statistica portiere da ottimizzare: selezionare un valore dal menù a tendina\n");
    		return;
    	}
    	if(cmbTeamStats.getValue()== null) {
    		txtResult.appendText("Errore in Statistica giocatori di movimento da ottimizzare: selezionare un valore dal menù a tendina\n");
    		return;
    	}

    	// Controllo minuti
    	if(minMinutesPlayed< 540) {
    		txtResult.appendText("Errore in Numero minimo minuti giocati: il valore inserito deve essere maggiore o uguale a 540\n");
    		return;
    	}
    	
    	// Controllo età
    	if(cmbMinAge.getValue()== null || cmbMaxAge.getValue()== null) {
    		txtResult.appendText("Errore in Età: selezionare un valore dal menù a tendina\n");
    		return;
    	}
    	if(cmbMinAge.getValue()> cmbMaxAge.getValue()) {
    		txtResult.appendText("Errore in Età: l'età minima deve esere minore o uguale a quella massima\n");
    		return;
    	}    	
    	
    	// Controllo player same team 
    	if(cmbPlayersSameTeam.getValue()== null) {
    		txtResult.appendText("Errore in Numero massimo giocatori di movimento per squadra: selezionare un valore dal menù a tendina\n");
    		return;
    	}
    	    	
    	double gValue= value * percentualePorValue;
    	double gWage= wage * percentualePorWage;
    	
    	String risultato=this.model.createDreamTeam(cmbFormation.getValue(), cmbGoalkeeperStats.getValue(),
    			cmbTeamStats.getValue(), value, wage, gValue, gWage, minMinutesPlayed,
    			cmbMinAge.getValue(), cmbMaxAge.getValue(), cmbPlayersSameTeam.getValue()); 
    	
    	if(risultato.startsWith("E"))
    		this.txtResult.appendText(risultato);
    	else
    		this.txtResult.setText(risultato);
    	
    	long endTime = System.currentTimeMillis();
    	System.out.println((endTime-startTime)/1000.00);
    }

    @FXML
    void createDreamTeamWithoutConstraints(ActionEvent event) {
    	// Controllo formazione
    	if(cmbFormation.getValue()== null) {
    		txtResult.setText("Errore in Modulo: selezionare un valore dal menù a tendina\n");
    		return;
    	}
    	
    	// Controllo caratteristiche 
    	if(cmbGoalkeeperStats.getValue()== null) {
    		txtResult.setText("Errore in Statistica portiere da ottimizzare: selezionare un valore dal menù a tendina\n");
    		return;
    	}
    	if(cmbTeamStats.getValue()== null) {
    		txtResult.setText("Errore in Statistica giocatori di movimento da ottimizzare: selezionare un valore dal menù a tendina\n");
    		return;
    	}
    	
    	// Inizializzo tutti i parametri della ricerca del DREAM TEAM con vincoli 
    	txtMaxValue.setText("");
    	txtGoalkeeperValue.setText("");
    	txtMaxSalary.setText("");
    	txtGoalkeeperSalary.setText("");
    	txtMinMinutesPlayed.setText("");
    	cmbMinAge.setValue(null);
    	cmbMaxAge.setValue(null);
    	cmbPlayersSameTeam.setValue(null);
    	
    	this.txtResult.setText(this.model.createDreamTeam(cmbFormation.getValue(), cmbGoalkeeperStats.getValue(), cmbTeamStats.getValue()));
    }

    @FXML
    void findClubStats(ActionEvent event) {
    	long startTime = System.currentTimeMillis();
    	if(cmbClub.getValue()== null) {
    		txtResult.setText("Errore: selzionare una squadra dal menù a tendina\n");
    		return;
    	}
    	txtResult.setText(this.model.getStatisticsClub(cmbClub.getValue().getClub()));
    	long endTime = System.currentTimeMillis();
    	System.out.println((endTime-startTime)/1000.00);
    }

    @FXML
    void findPlayerStats(ActionEvent event) {
    	long startTime = System.currentTimeMillis();
    	if(cmbPlayer.getValue()== null) {
    		txtResult.setText("Errore: selzionare un giocatore dal menù a tendina\n");
    		return;
    	}
    	txtResult.setText(this.model.getStatisticsPlayer(cmbPlayer.getValue()));
    	long endTime = System.currentTimeMillis();
    	System.out.println((endTime-startTime)/1000.00);
    }

    @FXML
    void initialize() {
        assert btnClubStats != null : "fx:id=\"btnClubStats\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeamConstraints != null : "fx:id=\"btnDreamTeamConstraints\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeamNoConstraints != null : "fx:id=\"btnDreamTeamNoConstraints\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPlayerStats != null : "fx:id=\"btnPlayerStats\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbClub != null : "fx:id=\"cmbClub\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbFormation != null : "fx:id=\"cmbFormation\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGoalkeeperStats != null : "fx:id=\"cmbGoalkeeperStats\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMaxAge != null : "fx:id=\"cmbMaxAge\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMinAge != null : "fx:id=\"cmbMinAge\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbPlayer != null : "fx:id=\"cmbPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbPlayersSameTeam != null : "fx:id=\"cmbPlayersSameTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbTeamStats != null : "fx:id=\"cmbTeamStats\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGoalkeeperSalary != null : "fx:id=\"txtGoalkeeperSalary\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGoalkeeperValue != null : "fx:id=\"txtGoalkeeperValue\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMaxSalary != null : "fx:id=\"txtMaxSalary\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMaxValue != null : "fx:id=\"txtMaxValue\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinMinutesPlayed != null : "fx:id=\"txtMinMinutesPlayed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
		this.model= model;
		
		// Players
		cmbPlayer.getItems().setAll(this.model.getAllPlayers());
		
		// Clubs 
		cmbClub.getItems().setAll(this.model.getAllTeams());
		
		// Formations
		cmbFormation.getItems().add("3 - 4 - 3");
		cmbFormation.getItems().add("3 - 5 - 2");
		cmbFormation.getItems().add("4 - 3 - 3");
		cmbFormation.getItems().add("4 - 4 - 2");
		cmbFormation.getItems().add("4 - 5 - 1");
		cmbFormation.getItems().add("5 - 3 - 2");
		cmbFormation.getItems().add("5 - 4 - 1");
		
		// Statistica Portiere 
		cmbGoalkeeperStats.getItems().add("saved / (conceded + saved)");
		cmbGoalkeeperStats.getItems().add("conceded / minutes played");
		cmbGoalkeeperStats.getItems().add("clean sheets / match played");

		// Statistica Team
		cmbTeamStats.getItems().add("balls recoverd / minutes played");
		cmbTeamStats.getItems().add("tackles won / tackles");
		cmbTeamStats.getItems().add("pass completed / pass attempted");
		cmbTeamStats.getItems().add("assist / minutes played");
		cmbTeamStats.getItems().add("shot on target / shot attempts");
		cmbTeamStats.getItems().add("goals / shot attempts");
		cmbTeamStats.getItems().add("goals / minutes played");
		cmbTeamStats.getItems().add("dribbles / minutes played");
		
		// Age
		List<Integer> ages= new ArrayList<>();
		for(int i=16; i<40; i++) 
			ages.add(i);
		
		cmbMinAge.getItems().setAll(ages);
		cmbMaxAge.getItems().setAll(ages);
		
		// Players same Team 
		List<Integer> lista= new ArrayList<>();
		for(int i=1; i<4; i++) 
			lista.add(i);
		
		cmbPlayersSameTeam.getItems().setAll(lista);
    }

}
