package my.snapshot.service;

import java.util.List;

import my.snapshot.ignite.model.PortfolioTradeInfo;

public interface PortfolioTradeInfoService {
	public void put(PortfolioTradeInfo portfolioTradeInfo);

	public List<String> getLogins();
	
	public List<PortfolioTradeInfo> getTradeInfos();

	public void init();
}
