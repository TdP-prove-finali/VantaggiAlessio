package it.polito.tdp.provaFinale.model;

import java.util.Objects;

public class Squadra {
	private String club;
	private int totalMatchPlayed;
	
	public Squadra(String club, int totalMatchPlayed) {
		this.club = club;
		this.totalMatchPlayed = totalMatchPlayed;
	}

	public String getClub() {
		return club;
	}

	public int getTotalMatchPlayed() {
		return totalMatchPlayed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(club);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Squadra other = (Squadra) obj;
		return Objects.equals(club, other.club);
	}

	@Override
	public String toString() {
		return this.club;
	}

	
	
}
