package my.snapshot.bean;

import java.io.Serializable;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class ForexTrade implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@QuerySqlField
	private String deal;
	private String login;
	private String type;
	
	public String getDeal() {
		return deal;
	}
	public void setDeal(String deal) {
		this.deal = deal;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
