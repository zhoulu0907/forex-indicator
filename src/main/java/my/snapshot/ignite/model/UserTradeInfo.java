package my.snapshot.ignite.model;

import java.io.Serializable;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import lombok.Data;

@Data
public class UserTradeInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserTradeInfoKey id;
	private String deal;
	@QuerySqlField
	private String login;
	private String type;
	@QuerySqlField
	private String symbol;
	private Double volume;
	private Long holdtime;//s
	private Double profit;
		
}
