package domain;

import java.util.List;

public class Classifica {
	
	private List<String> username;
	private List<Integer> punti;
	private List<Integer> match;
	
	public Classifica() {
	}
	
	public Classifica(List<String> user, List<Integer> punti, List<Integer> match) {
		
		this.username = user;
		this.punti = punti;
		this.match = match;
	}

	public List<String> getUsername() {
		return username;
	}
	public void setUsername(List<String> user) {
		this.username=user;
	}
	
	public List<Integer> getPunti() {
		return punti;
	}
	public void setPunti(List<Integer> points) {
		this.punti=points;
	}
	
	public List<Integer> getMatch() {
		return match;
	}
	public void setMatch(List<Integer> mat) {
		this.match=mat;
	}

}
