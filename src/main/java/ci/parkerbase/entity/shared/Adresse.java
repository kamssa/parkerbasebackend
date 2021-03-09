package ci.parkerbase.entity.shared;

import javax.persistence.Embeddable;

@Embeddable
public class Adresse {
	private String boitePostal;
   
	private String pays;
	private String ville;
	private String siteWeb;
	private  String telephone;
	public String getBoitePostal() {
		return boitePostal;
	}
	
	public Adresse() {
		super();
	}

public Adresse(String boitePostal, String pays, String ville, String siteWeb, String telephone) {
		super();
		this.boitePostal = boitePostal;
		this.pays = pays;
		this.ville = ville;
		this.siteWeb = siteWeb;
		this.telephone = telephone;
	}

	public void setBoitePostal(String boitePostal) {
		this.boitePostal = boitePostal;
	}
	
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	public String getSiteWeb() {
		return siteWeb;
	}
	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
