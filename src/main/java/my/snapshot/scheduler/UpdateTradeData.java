package my.snapshot.scheduler;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ignite.Ignite;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.springframework.stereotype.Component;

import my.snapshot.bean.ForexTrade;
import my.snapshot.service.ForexTradeService;

@Component
public class UpdateTradeData {
	
	@Resource(name="ForexTradeServiceMysql")
	private ForexTradeService forexTradeService;
		
	public void updateTradeDataEveryDay() {
		List<ForexTrade> forexTradeList = forexTradeService.findAll();
	}
		
}
