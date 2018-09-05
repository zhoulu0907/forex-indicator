package my.snapshot.ignite.cachestore;

import javax.cache.configuration.Factory;

import org.apache.ignite.cache.store.CacheStore;

import my.snapshot.bean.ForexTrade;

public class ForexTradeCacheFactory implements Factory<ForexCacheStore> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ForexCacheStore create() {
		// TODO Auto-generated method stub
		return new ForexCacheStore();
	}

}
