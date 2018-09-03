package my.snapshot.runner;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ignite.Ignite;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import my.snapshot.bean.ForexTrade;
import my.snapshot.ignite.IgniteInitation;
import my.snapshot.service.ForexTradeService;

//@Component
@Order(value=1)
public class ForexTradeSnap implements CommandLineRunner {

	@Resource(name="ForexService")
	private ForexTradeService forexTradeService;
	@Resource
	private IgniteInitation igniteInitation;
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		//test ignite instance
		Ignite ignite = igniteInitation.getIgniteInstance();
		ignite.compute().run(new IgniteRunnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Ignite ok.");
			}
			
		});
		//初始化Ignite Cache
		igniteInitation.InitIgniteCache();
		//获取所有交易数据
		List<ForexTrade> forexTradeList = forexTradeService.findAll();
		for (ForexTrade forexTrade : forexTradeList) {
			System.out.println(forexTrade.getDeal()
					+ ", " + forexTrade.getLogin() 
					+ ", " + forexTrade.getType());
			
		}
	}

}
