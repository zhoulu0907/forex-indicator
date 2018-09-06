package my.snapshot.runner;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import my.snapshot.bean.ForexTrade;
import my.snapshot.constants.IgniteConstants;
import my.snapshot.ignite.IgniteManager;
import my.snapshot.ignite.model.PortfolioTradeInfo;
import my.snapshot.service.ForexTradeService;
import my.snapshot.service.PortfolioTradeInfoService;

/**
 * @author Administrator
 * TBD
 */
@Component
@Order(value=2)
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
			System.out.println("login size: " + loginList.size()
					+ ", use: " + (System.currentTimeMillis() - startT) + " ms."
					);
			Thread.sleep(5 * 1000);
		}
	}

}
