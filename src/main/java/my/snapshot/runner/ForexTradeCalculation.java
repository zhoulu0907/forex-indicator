package my.snapshot.runner;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import my.snapshot.ignite.IgniteManager;
import my.snapshot.ignite.model.UserTradeInfo;
import my.snapshot.service.PortfolioTradeInfoService;

/**
 * @author Administrator
 * TBD
 */
@Component
@Order(value=2)
@Slf4j
public class ForexTradeCalculation implements CommandLineRunner{

	@Resource
	private IgniteManager igniteManager;
	
	@Resource(name="PortfolioTradeInfoService")
	private PortfolioTradeInfoService portfolioTradeInfoService;
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub

		while(true) {
			long startT = System.currentTimeMillis();
			List<String> loginList = portfolioTradeInfoService.getLogins();
			log.info("login size: " + loginList.size()
					+ ", use: " + (System.currentTimeMillis() - startT) + " ms."
					);
			for (String login : loginList) {
				List<String> symbolList = portfolioTradeInfoService.getSymbolsOfLogin(login);
				for (String symbol : symbolList) {
					List<UserTradeInfo> tradeInfoList = 
							portfolioTradeInfoService.getTradeInfos(login, symbol);
					log.info("Get login: " + login
							+ ", symbol: " + symbol
							+ ", size: " + tradeInfoList.size()
							);
				}
			}
			Thread.sleep(5 * 1000);
		}
	}

}
