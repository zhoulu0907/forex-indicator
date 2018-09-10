package my.snapshot.service;

import java.util.List;

import my.snapshot.ignite.model.UserTradeInfo;

public interface PortfolioTradeInfoService {
	public void put(UserTradeInfo portfolioTradeInfo);

	public List<String> getLogins();
	
	public List<UserTradeInfo> getTradeInfos();

	public void init();

	public List<UserTradeInfo> getTradeInfos(String login);

	public List<String> getSymbolsOfLogin(String login);

	public List<UserTradeInfo> getTradeInfos(String login, String symbol);
}
