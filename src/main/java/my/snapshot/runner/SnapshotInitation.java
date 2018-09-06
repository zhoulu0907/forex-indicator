package my.snapshot.runner;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import my.snapshot.ignite.IgniteManager;
import my.snapshot.service.PortfolioTradeInfoService;

@Component
@Order(value=0)
public class SnapshotInitation implements CommandLineRunner {

	@Resource
	private IgniteManager igniteInitation;
	@Resource
	private PortfolioTradeInfoService portfolioTradeInfoService;
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("----Snapshot Initation----");
		igniteInitation.getIgniteInstance().cluster().active(true);
		igniteInitation.InitIgniteCache();
		
		portfolioTradeInfoService.init();
	}

}
