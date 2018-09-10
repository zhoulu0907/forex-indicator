package my.snapshot.ignite.model;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

import lombok.Data;
@Data
public class UserIndicatorKey {

	@AffinityKeyMapped
	private String login;
	
	private String symbol;

	
}
