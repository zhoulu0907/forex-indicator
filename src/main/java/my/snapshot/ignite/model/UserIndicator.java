package my.snapshot.ignite.model;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import lombok.Data;

@Data
public class UserIndicator {
	private UserIndicatorKey id;

	@QuerySqlField
	private String login;
	
	@QuerySqlField
	private String symbol;
	
	private Double predictVolume;
	
	private long predictHoldTime;
	
	
}
