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
import my.snapshot.service.ForexTradeService;

//@Component
@Order(value=1)
public class ForexTradeSnap implements CommandLineRunner {

	@Resource(name="ForexTradeServiceMysql")
	private ForexTradeService forexTradeService;
	@Resource
	private IgniteManager igniteManager;
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		Ignite ignite = igniteManager.getIgniteInstance();
		//初始化Ignite Cache
//		igniteInitation.InitIgniteCache();
		long startT = System.currentTimeMillis();
		//获取所有交易数据
//		List<ForexTrade> forexTradeList = forexTradeService.findAll();
		List<ForexTrade> forexTradeList = forexTradeService.find(0,1000);
//		for (ForexTrade forexTrade : forexTradeList) {
//			System.out.println(forexTrade.getDeal()
//					+ ", " + forexTrade.getLogin() 
//					+ ", " + forexTrade.getType());
//			
//		}
		System.out.println("forex trade data size: " + forexTradeList.size()
				+ ", use:" +(System.currentTimeMillis() - startT)
				);
		
//		ignite.cluster().disableWal(IgniteConstants.CACHE_NAME_FOREX_TRADE);
		IgniteCache<String, ForexTrade> forexTradeCache = ignite.cache(IgniteConstants.CACHE_NAME_FOREX_TRADE);
		//1.Save data one by one
//		long startT = System.currentTimeMillis();
//		for (ForexTrade forexData : forexTradeList) {
//			forexTradeCache.put(forexData.getDeal(), forexData);
//		}
//		System.out.println("Save data one by one use: " 
//				+ (System.currentTimeMillis() - startT) + "ms.");
//		boolean checkRst = CheckIgniteSave(forexTradeCache, forexTradeList);
//		System.out.println("Check result: " + checkRst);
		//2.Save data by block
//		long startT = System.currentTimeMillis();
//		Map<String, ForexTrade> forexMap = forexTradeList.stream().collect(Collectors.toMap(ForexTrade::getDeal, a->a, (k1,k2)->k1));
//		forexTradeCache.putAll(forexMap);
//		System.out.println("Save data by block use: " 
//				+ (System.currentTimeMillis() - startT) + "ms.");
//		boolean checkRst = CheckIgniteSave(forexTradeCache, forexTradeList);
//		System.out.println("Check result: " + checkRst);
		//3.two batch
//		int saveNum = 0;
//		for (int loop = 0; loop < (int)(forexTradeList.size()/2); loop++) {
//			ForexTrade forexData = forexTradeList.get(loop);
//			forexTradeCache.put(forexData.getDeal(), forexData);
//			saveNum++;
//		}
//		System.out.println("Batch 1 save: " + saveNum + ", wal: " + ignite.cluster().isWalEnabled(IgniteConstants.CACHE_NAME_FOREX_TRADE));
//		saveNum = 0;
//
//		ignite.cluster().disableWal(IgniteConstants.CACHE_NAME_FOREX_TRADE);
//		IgniteCache<String, ForexTrade> forexTradeCache2 = ignite.cache(IgniteConstants.CACHE_NAME_FOREX_TRADE);
//		for (int loop = (int)(forexTradeList.size()/2); loop < forexTradeList.size(); loop++) {
//			ForexTrade forexData = forexTradeList.get(loop);
//			forexTradeCache2.put(forexData.getDeal(), forexData);
//			saveNum++;
//		}
//		System.out.println("Batch 2 save: " + saveNum + ", wal: " + ignite.cluster().isWalEnabled(IgniteConstants.CACHE_NAME_FOREX_TRADE));
		//4. 1 min life cache
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int loop = 0;
				while(true) {
					IgniteCache<String, ForexTrade> forexTradeCache1 = ignite.cache(IgniteConstants.CACHE_NAME_FOREX_TRADE);
					IgniteCache<String, ForexTrade> forexTradeCacheTmp = forexTradeCache1.withExpiryPolicy(new CreatedExpiryPolicy(Duration.ONE_MINUTE));
					long startT = System.currentTimeMillis();
					
					for (ForexTrade forexData : forexTradeList) {
						String batchId = UUID.randomUUID().toString();
						String forexKey = forexData.getDeal() + "_" + batchId;
						forexTradeCacheTmp.put(forexKey, forexData);
					}
					System.out.println("Save"
							+ " batch: " + loop
							+ ", data one by one use: " 
							+ (System.currentTimeMillis() - startT) + "ms.");
					try {
						Thread.sleep(5 * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (loop++ > 100)break;
				}
			}
		}.start();
	}
	
//	private Boolean CheckIgniteSave(IgniteCache<String, ForexTrade> forexTradeCache, List<ForexTrade> forexTradeList) {
//		List<String> dealList = forexTradeList.stream().map(ForexTrade::getDeal).collect(Collectors.toList());
//		// TODO Auto-generated method stub
//		SqlQuery<String, ForexTrade> sql = new SqlQuery<String, ForexTrade>(
//				ForexTrade.class, "deal != ?");
//		Boolean checkFlag = true;
//		try(QueryCursor<Entry<String, ForexTrade>> cursor = 
//				forexTradeCache.query(sql.setArgs(""))){
//			for (Entry<String, ForexTrade> e : cursor) {
//				ForexTrade forexTrade = e.getValue();
//				if (!dealList.contains(forexTrade.getDeal())) {
//					checkFlag = false;
//					break;
//				}
//			}
//		}
//		Ignite ignite = igniteManager.getIgniteInstance();
//		//初始化Ignite Cache
////		igniteInitation.InitIgniteCache();
//		ignite.cluster().disableWal(IgniteConstants.CACHE_NAME_FOREX_TRADE);
//		//获取所有交易数据
//		List<ForexTrade> forexTradeList = forexTradeService.findAll();
////		for (ForexTrade forexTrade : forexTradeList) {
////			System.out.println(forexTrade.getDeal()
////					+ ", " + forexTrade.getLogin() 
////					+ ", " + forexTrade.getType());
////			
////		}
//		System.out.println("forex trade data size: " + forexTradeList.size());
//		IgniteCache<String, ForexTrade> forexTradeCache = ignite.cache(IgniteConstants.CACHE_NAME_FOREX_TRADE);
//		//1.Save data one by one
//		long startT = System.currentTimeMillis();
//		int loop = 0;
//		for (ForexTrade forexData : forexTradeList) {
//			if (loop > 500) {
//				loop++;
//				ignite.cluster().enableWal(IgniteConstants.CACHE_NAME_FOREX_TRADE);
//			}
//			System.out.println(ignite.cluster().isWalEnabled(IgniteConstants.CACHE_NAME_FOREX_TRADE));
//			forexTradeCache.put(forexData.getDeal(), forexData);
//		}	
//		System.out.println("Save data one by one use: " 
//				+ (System.currentTimeMillis() - startT) + "ms.");
////		boolean checkRst = CheckIgniteSave(forexTradeCache, forexTradeList);
////		System.out.println("Check result: " + checkRst);
//		//2.Save data by block
////		long startT = System.currentTimeMillis();
////		Map<String, ForexTrade> forexMap = forexTradeList.stream().collect(Collectors.toMap(ForexTrade::getDeal, a->a, (k1,k2)->k1));
////		forexTradeCache.putAll(forexMap);
////		System.out.println("Save data by block use: " 
////				+ (System.currentTimeMillis() - startT) + "ms.");
////		boolean checkRst = CheckIgniteSave(forexTradeCache, forexTradeList);
////		System.out.println("Check result: " + checkRst);
//	}
////	private Boolean CheckIgniteSave(IgniteCache<String, ForexTrade> forexTradeCache, List<ForexTrade> forexTradeList) {
////		List<String> dealList = forexTradeList.stream().map(ForexTrade::getDeal).collect(Collectors.toList());
////		// TODO Auto-generated method stub
////		SqlQuery<String, ForexTrade> sql = new SqlQuery<String, ForexTrade>(
////				ForexTrade.class, "deal != ?");
////		Boolean checkFlag = true;
////		try(QueryCursor<Entry<String, ForexTrade>> cursor = 
////				forexTradeCache.query(sql.setArgs(""))){
////			for (Entry<String, ForexTrade> e : cursor) {
////				ForexTrade forexTrade = e.getValue();
////				if (!dealList.contains(forexTrade.getDeal())) {
////					checkFlag = false;
////					break;
////				}
////			}
////		}
////		return checkFlag;
////	}
}
