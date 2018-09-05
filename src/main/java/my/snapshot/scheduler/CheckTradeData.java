package my.snapshot.scheduler;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import my.snapshot.bean.ForexTrade;
import my.snapshot.constants.IgniteConstants;
import my.snapshot.ignite.IgniteManager;
import my.snapshot.service.ForexTradeService;

//@Component
public class CheckTradeData {

	@Resource
	private IgniteManager igniteManager;
	
	@Resource(name="ForexTradeServiceIgnite")
	private ForexTradeService forexTradeService;
	
	
	@Scheduled(initialDelay = 10 * 1000, fixedDelay = 10 * 1000)
	public void CheckForexTradeData() {
		igniteManager.getIgniteInstance().cluster().enableWal(IgniteConstants.CACHE_NAME_FOREX_TRADE);
		long startT = System.currentTimeMillis();
		List<ForexTrade> forexTradeList = forexTradeService.findAll();
		System.out.println("ignite forex data size: " + forexTradeList.size()
			+ ", use: " + (System.currentTimeMillis() - startT) + " ms."
			+ ", wal: " + igniteManager.getIgniteInstance().cluster().isWalEnabled(IgniteConstants.CACHE_NAME_FOREX_TRADE)
			);
	}
}
