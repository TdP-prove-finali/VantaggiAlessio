package it.polito.tdp.provaFinale.model;

import java.text.DecimalFormat;

public class DreamTeam implements Comparable<DreamTeam>{
	
	private String name;
	private String position;
	private String club;
	private int age;
	private double fifaWage;
	private double fifaValue; 
	private int minutesPlayed;
	private String statsName;
	private double statsValue;

	public DreamTeam(String name, String position, String club, int age, double fifaWage, double fifaValue,
			int minutesPlayed, String statsName, double statsValue) {
		this.name = name;
		this.position = position;
		this.club = club;
		this.age = age;
		this.fifaWage = fifaWage;
		this.fifaValue = fifaValue;
		this.minutesPlayed = minutesPlayed;
		this.statsName = statsName;
		this.statsValue = statsValue;
	}

	public String getName() {
		return name;
	}



	public String getPosition() {
		return position;
	}



	public String getClub() {
		return club;
	}



	public int getAge() {
		return age;
	}



	public double getFifaWage() {
		return fifaWage;
	}



	public double getFifaValue() {
		return fifaValue;
	}



	public int getMinutesPlayed() {
		return minutesPlayed;
	}



	public String getStatsName() {
		return statsName;
	}



	public double getStatsValue() {
		return statsValue;
	}



	@Override
	public int compareTo(DreamTeam other) {
		return -this.position.compareTo(other.getPosition());
	}

	@Override
	public String toString() {
		 DecimalFormat decimalFormat = new DecimalFormat("#,###");
		return ""+this.name+",  "+this.club+",  "+this.age+"  anni,  FIFA value: "+decimalFormat.format(this.fifaValue)+ " €,  FIFA wage: "+decimalFormat.format(this.fifaWage)+ " €,  "+this.statsName+": "+this.statsValue;
	}	
	
	

}
