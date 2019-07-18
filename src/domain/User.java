package domain;

public class User {
	
	private String id;
	private String username;
	private String password;
	private String nome;
	private String email;
	private String bio;
	private String datanascita;
	private String avatar;
	
	public User() {
		
	}
	
	public User(String id,String user,String pass,String name,String email,String bio,String dtn, String avatar) {
		this.id=id;
		this.username=user;
		this.password=pass;
		this.nome=name;
		this.email=email;
		this.bio=bio;
		this.datanascita=dtn;
		this.avatar = avatar;
		
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String user) {
		this.username=user;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String pass) {
		this.password=pass;
	}
	
	public String getNome() {
		return nome;
	}
	public void setName(String name) {
		this.nome=name;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio=bio;
	}
	
	public String getDataNascita() {
		return datanascita;
	}
	public void setDataNascita(String dtn) {
		this.datanascita=dtn;
	}
	
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id=id;
	}
	
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar=avatar;
	}
}
