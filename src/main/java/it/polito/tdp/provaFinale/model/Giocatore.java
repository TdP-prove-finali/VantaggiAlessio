package it.polito.tdp.provaFinale.model;

import java.util.Objects;

public class Giocatore {
	private int IDGiocatore;
	private String name;
	private String position;
	private int age;
	private String nationality;
	private double fifaWage;
	private double fifaValue;
	
	public Giocatore(int iDGiocatore, String name, String position, int age, String nationality, double fifaWage,
			double fifaValue) {
		IDGiocatore = iDGiocatore;
		this.name = name;
		this.position = position;
		this.age = age;
		this.nationality = nationality;
		this.fifaWage = fifaWage;
		this.fifaValue = fifaValue;
	}

	public int getIDGiocatore() {
		return IDGiocatore;
	}

	public String getName() {
		return name;
	}

	public String getPosition() {
		return position;
	}

	public int getAge() {
		return age;
	}

	public String getNationality() {
		return nationality;
	}

	public double getFifaWage() {
		return fifaWage;
	}

	public double getFifaValue() {
		return fifaValue;
	}

	@Override
	public int hashCode() {
		return Objects.hash(IDGiocatore);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		return IDGiocatore == other.IDGiocatore;
	}

	@Override
	public String toString() {
		return this.name;
	}	

}
