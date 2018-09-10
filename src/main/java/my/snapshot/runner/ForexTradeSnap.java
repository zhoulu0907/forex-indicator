package my.snapshot.runner;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import my.snapshot.bean.ForexTrade;
import my.snapshot.ignite.model.UserTradeInfoKey;
import my.snapshot.ignite.model.UserTradeInfo;
import my.snapshot.service.ForexTradeService;
import my.snapshot.service.PortfolioTradeInfoService;

/**
 * @author Administrator
 * 从mysql中获取trade数据，加载到ignite中
 * 
 * 
 */
@Component
@Order(value=1)
public class ForexTradeSnap implements CommandLineRunner {

	@Resource(name="ForexTradeServiceMysql")
	private ForexTradeService forexTradeServiceDb;
	@Resource(name="PortfolioTradeInfoService")
	private PortfolioTradeInfoService portfolioTradeInfoService;
	@Override
	public void run(String... arg0) throws Exception {
		SaveTradeInfo();
		
		new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					SaveTradeInfo();
					try {
						Thread.sleep(5 * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
	}
	private void SaveTradeInfo() {
		List<ForexTrade> forexTradeList = forexTradeServiceDb.find(0, 1000);
		if (forexTradeList == null || forexTradeList.size() == 0) {
			return;
		}
		for (ForexTrade forexTrade : forexTradeList) {
			UserTradeInfoKey portfolioKey = new UserTradeInfoKey();
			portfolioKey.setDeal(forexTrade.getDeal());
			portfolioKey.setLogin(forexTrade.getLogin());
			
			UserTradeInfo tradeInfo = new UserTradeInfo();
			tradeInfo.setId(portfolioKey);
			tradeInfo.setDeal(forexTrade.getDeal());
			tradeInfo.setLogin(forexTrade.getLogin());
			tradeInfo.setSymbol(forexTrade.getSymbol());
			tradeInfo.setType(forexTrade.getType());
			tradeInfo.setVolume(forexTrade.getVolume());
			Timestamp opentime = forexTrade.getOpentime();
			Timestamp closetime = forexTrade.getClosetime();
			long holdtime = (closetime.getTime() - opentime.getTime()) / 1000;
			tradeInfo.setHoldtime(holdtime);
			tradeInfo.setProfit(forexTrade.getProfit());
			
			portfolioTradeInfoService.put(tradeInfo);
		}
	}
	
}
