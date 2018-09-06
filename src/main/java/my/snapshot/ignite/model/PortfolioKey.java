package my.snapshot.ignite.model;

import java.io.Serializable;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

public class PortfolioKey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@AffinityKeyMapped
	private String login;
	private String deal;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getDeal() {
		return deal;
	}
	public void setDeal(String deal) {
		this.deal = deal;
	}
	
}
