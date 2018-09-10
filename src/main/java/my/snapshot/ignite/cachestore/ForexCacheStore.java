package my.snapshot.ignite.cachestore;

import javax.cache.Cache.Entry;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;

import org.apache.ignite.cache.store.CacheStoreAdapter;

import my.snapshot.ignite.model.UserTradeInfoKey;
import my.snapshot.ignite.model.UserTradeInfo;

public class ForexCacheStore extends CacheStoreAdapter<UserTradeInfoKey, UserTradeInfo>{

	@Override
	public UserTradeInfo load(UserTradeInfoKey arg0) throws CacheLoaderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Object arg0) throws CacheWriterException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(Entry<? extends UserTradeInfoKey, ? extends UserTradeInfo> arg0) throws CacheWriterException {
		// TODO Auto-generated method stub
		
	}



}
