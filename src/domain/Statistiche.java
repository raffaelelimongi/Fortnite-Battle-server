package domain;

public class Statistiche {
	
	private Integer partitegiocate;
	private Integer kills;
	private Integer vittorie;
	private Double punteggio;
	private Integer top5;
	private Integer top10;
	private String modalita;
	private Double kd;
	private Double winrate;
	
	public Statistiche(Integer matchesplayed, Integer kills, Integer wins, Double score,Integer top5,Integer top10, String type, Double kd,Double winrate) {
		
		this.partitegiocate=matchesplayed;
		this.kills=kills;
		this.vittorie=wins;
		this.punteggio=score;
		this.top5=top5;
		this.top10=top10;
		this.modalita=type;
		this.kd=kd;
		this.winrate = winrate;
	}


	public Double getWinrate() {
		return winrate;
	}
	public void setWinrate(Double winrate) {
		this.winrate=winrate;
	}
	
	public Integer getPartiteGiocate() {
		return partitegiocate;
	}
	public void setPartiteGiocate(Integer pg) {
		this.partitegiocate=pg;
	}
	
	public Integer getKills() {
		return kills;
	}
	public void setKills(Integer kills) {
		this.kills=kills;
	}
	
	public Integer getVittorie() {
		return vittorie;
	}
	public void setVittorie(Integer win) {
		this.vittorie=win;
	}
	
	public Double getPunteggio() {
		return punteggio;
	}
	public void setPunteggio(Double score) {
		this.punteggio=score;
	}
	
	public Integer getTop5() {
		return top5;
	}
	public void setTop5(Integer top5) {
		this.top5=top5;
	}
	
	public Integer getTop10() {
		return top10;
	}
	public void setTop10(Integer top10) {
		this.top10=top10;
	}
	
	public String getTipoModalita() {
		return modalita;
	}
	public void setTipoModalita(String type) {
		this.modalita=type;
	}
	
	public Double getKD() {
		return kd;
	}
	public void setKD(Double kd) {
		this.kd=kd;
	}
	
	
	

}
