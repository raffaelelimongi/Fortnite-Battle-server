package domain;

public class Arma {
	
	private String nome;
	private String rarita;
	private String image;
	private Double dannicorpo;
	private Double dannitesta;
	private Double range;
	private Double ricarica;
	private Integer caricatore;
	
	public Arma(String name, String rar, String img, Double dc, Double dt, Double range, Double reload, Integer caricatore) {
		
		this.nome = name;
		this.rarita = rar;
		this.image = img;
		this.dannicorpo = dc;
		this.dannitesta = dt;
		this.range = range;
		this.ricarica = reload;
		this.caricatore = caricatore;
	}

	public String getNome() {
		return nome;
	}
	public void setName(String name) {
		this.nome=name;
	}
	
	public String getRarita() {
		return rarita;
	}
	public void setRarita(String rar) {
		this.rarita=rar;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String img) {
		this.image=img;
	}
	
	public Double getDanniCorpo() {
		return dannicorpo;
	}
	public void setDanniCorpo(Double dc) {
		this.dannicorpo=dc;
	}
	
	public Double getDanniTesta() {
		return dannitesta;
	}
	public void setDanniTesta(Double dt) {
		this.dannitesta=dt;
	}
	
	public Double getRange() {
		return range;
	}
	public void setRange(Double range) {
		this.range=range;
	}
	
	public Double getReload() {
		return ricarica;
	}
	public void setReload(Double reload) {
		this.ricarica=reload;
	}
	
	public Integer getCaricatore() {
		return caricatore;
	}
	public void setCaricatore(Integer caricatore) {
		this.caricatore=caricatore;
	}
	
}
