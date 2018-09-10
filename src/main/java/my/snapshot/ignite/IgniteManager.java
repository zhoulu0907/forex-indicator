package my.snapshot.ignite;

import javax.annotation.Resource;
import javax.cache.configuration.Factory;
import javax.cache.configuration.FactoryBuilder;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import my.snapshot.bean.ForexTrade;
import my.snapshot.constants.IgniteConstants;
import my.snapshot.ignite.cachestore.ForexTradeCacheFactory;
import my.snapshot.ignite.model.UserTradeInfoKey;
import my.snapshot.ignite.model.UserTradeInfo;
import my.snapshot.service.PortfolioTradeInfoService;

@Component
public class IgniteManager implements ApplicationContextAware{

	private Ignite ignite;
		
	public void InitIgniteCache() {
		//1.init forextrade cache
		//1.1 forex-trade-cache
		CacheConfiguration<UserTradeInfoKey, UserTradeInfo> forexTradeCfg = 
				new CacheConfiguration<UserTradeInfoKey, UserTradeInfo>();
		forexTradeCfg.setName(IgniteConstants.CACHE_NAME_FOREX_TRADE);
		forexTradeCfg.setIndexedTypes(UserTradeInfoKey.class, UserTradeInfo.class);
//		forexTradeCfg.setCacheStoreFactory(new ForexTradeCacheFactory());
//		forexTradeCfg.setReadThrough(true);
//		forexTradeCfg.setWriteThrough(true);
		ignite.getOrCreateCache(forexTradeCfg);
		
	}

	@Override
	public void setApplicationContext(ApplicationContext cnt){
		// TODO Auto-generated method stub
		this.ignite = cnt.getBean(Ignite.class);
	}
	
	public Ignite getIgniteInstance() {
		return this.ignite;
	}
}
