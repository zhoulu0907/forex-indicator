package my.snapshot.ignite.cachestore;

import javax.cache.configuration.Factory;


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
