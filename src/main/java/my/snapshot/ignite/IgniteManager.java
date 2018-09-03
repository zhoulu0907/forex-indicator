package my.snapshot.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import my.snapshot.bean.ForexTrade;
import my.snapshot.constants.IgniteConstants;

@Component
public class IgniteManager implements ApplicationContextAware{

	private Ignite ignite;
	
	private IgniteCache<String, ForexTrade> forexTradeCache;
	
	public void InitIgniteCache() {
		//1.init forextrade cache
		//1.1 forex-trade-cache
		CacheConfiguration<String, ForexTrade> forexTradeCfg = new CacheConfiguration<String, ForexTrade>();
		forexTradeCfg.setName(IgniteConstants.CACHE_NAME_FOREX_TRADE);
		forexTradeCfg.setIndexedTypes(String.class, ForexTrade.class);
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