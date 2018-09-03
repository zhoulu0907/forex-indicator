package my.snapshot.runner;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import my.snapshot.bean.ForexTrade;
import my.snapshot.ignite.IgniteManager;
import my.snapshot.service.ForexTradeService;

@Component
@Order(value=2)
public class ForexTradeCalculation implements CommandLineRunner{

	@Resource
	private IgniteManager igniteManager;
	
	@Resource(name="ForexTradeServiceIgnite")
	private ForexTradeService forexTradeService;
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		long startT = System.currentTimeMillis();
		List<ForexTrade> forexTradeList = forexTradeService.findAll();
		System.out.println("ignite forex data size: " + forexTradeList.size()
			+ ", use: " + (System.currentTimeMillis() - startT) + " ms.");
	}

}
