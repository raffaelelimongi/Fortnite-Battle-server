package domain;

public class Torneo {
	
	private String id;
	private String nome;
	private String descrizione;
	private String datainizio;
	private Integer partecipanti;
	private String durata;
	private String regolamento;
	private String premio1;
	private String premio2;
	private String premio3;
	private String username;
	
	public Torneo() {
		
	}
	
	public Torneo(String id,String name, String desc,String dti, Integer nplayers,String durata,String reg, String p1, String p2, String p3, String user) {
		this.id = id;
		this.nome=name;
		this.descrizione=desc;
		this.datainizio=dti;
		this.partecipanti=nplayers;
		this.durata=durata;
		this.regolamento = reg;
		this.premio1 = p1;
		this.premio2 = p2;
		this.premio3 = p3;
		this.username = user;
	}
	
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id=id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String name) {
		this.nome=name;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String desc) {
		this.descrizione=desc;
	}
	
	public String getDatainizio() {
		return datainizio;
	}
	public void setDatainizio(String dti) {
		this.datainizio=dti;
	}
	
	public Integer getPartecipanti() {
		return partecipanti;
	}
	public void setPartecipanti(Integer nplayers) {
		this.partecipanti=nplayers;
	}
	
	public String getDurata() {
		return durata;
	}
	public void setDurata(String durata) {
		this.durata=durata;
	}
	
	public String getRegolamneto() {
		return regolamento;
	}
	public void setRegolamento(String reg) {
		this.regolamento=reg;
	}
	
	public String getPremio1() {
		return premio1;
	}
	public void setPremio1(String p1) {
		this.premio1=p1;
	}
	
	public String getPremio2() {
		return premio2;
	}
	public void setPremio2(String p2) {
		this.premio2=p2;
	}
	
	public String getPremio3() {
		return premio3;
	}
	public void setPremio3(String p3) {
		this.premio3=p3;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String user) {
		this.username=user;
	}

}
