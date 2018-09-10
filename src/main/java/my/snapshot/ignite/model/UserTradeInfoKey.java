package my.snapshot.ignite.model;

import java.io.Serializable;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

import lombok.Data;

@Data
public class UserTradeInfoKey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@AffinityKeyMapped
	private String login;
	private String deal;
	
}
