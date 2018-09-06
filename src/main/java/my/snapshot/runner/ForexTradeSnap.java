package my.snapshot.runner;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.cache.Cache.Entry;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlQuery;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import my.snapshot.bean.ForexTrade;
import my.snapshot.constants.IgniteConstants;
import my.snapshot.ignite.IgniteManager;
import my.snapshot.ignite.model.PortfolioKey;
import my.snapshot.ignite.model.PortfolioTradeInfo;
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
			PortfolioKey portfolioKey = new PortfolioKey();
			portfolioKey.setDeal(forexTrade.getDeal());
			portfolioKey.setLogin(forexTrade.getLogin());
			
			PortfolioTradeInfo tradeInfo = new PortfolioTradeInfo();
			tradeInfo.setId(portfolioKey);
			tradeInfo.setDeal(forexTrade.getDeal());
			tradeInfo.setLogin(forexTrade.getLogin());
			tradeInfo.setSymbol(forexTrade.getSymbol());
			tradeInfo.setType(forexTrade.getType());
			tradeInfo.setVolume(forexTrade.getVolume());
			
			portfolioTradeInfoService.put(tradeInfo);
		}
	}
	
}
