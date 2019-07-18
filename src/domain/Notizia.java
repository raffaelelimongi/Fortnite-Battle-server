package domain;


public class Notizia {
	
	private String titolo;
	private String data;
	private String descrizione;
	private String copertina;
	
	public Notizia(String titolo,String descrizione,String copertina,String data) {
		this.titolo=titolo;
		this.descrizione=descrizione;
		this.copertina=copertina;
		this.data=data;
		
	}
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String tit) {
		this.titolo=tit;
	}
	
	public String getData() {
		return data;
	}
	public void setData(String date) {
		this.data=date;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String desc) {
		this.descrizione=desc;
	}
	
	public String getCopertina() {
		return copertina;
	}
	public void setCopertina(String copertina) {
		this.copertina=copertina;
	}

}
