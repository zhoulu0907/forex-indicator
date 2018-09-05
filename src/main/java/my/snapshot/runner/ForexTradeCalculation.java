package my.snapshot.runner;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import my.snapshot.bean.ForexTrade;
import my.snapshot.constants.IgniteConstants;
import my.snapshot.ignite.IgniteManager;
import my.snapshot.service.ForexTradeService;

@Component
@Order(value=3)
public class ForexTradeCalculation implements CommandLineRunner{

	@Resource
	private IgniteManager igniteManager;
	
	@Resource(name="ForexTradeServiceIgnite")
	private ForexTradeService forexTradeService;
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub

		igniteManager.getIgniteInstance().cluster().enableWal(IgniteConstants.CACHE_NAME_FOREX_TRADE);
		long startT = System.currentTimeMillis();
		List<ForexTrade> forexTradeList = forexTradeService.findAll();
		for (ForexTrade forextrade : forexTradeList) {
			System.out.println("id: " + forextrade.getDeal() + ", login: " + forextrade.getLogin());
		}
		System.out.println("ignite forex data size: " + forexTradeList.size()
			+ ", use: " + (System.currentTimeMillis() - startT) + " ms."
			+ ", wal: " + igniteManager.getIgniteInstance().cluster().isWalEnabled(IgniteConstants.CACHE_NAME_FOREX_TRADE)
			);
	}

}
