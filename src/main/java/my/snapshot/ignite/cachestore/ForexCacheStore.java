package my.snapshot.ignite.cachestore;

import javax.cache.Cache.Entry;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;

import org.apache.ignite.cache.store.CacheStoreAdapter;

import my.snapshot.bean.ForexTrade;

public class ForexCacheStore extends CacheStoreAdapter<String, ForexTrade>{

	@Override
	public ForexTrade load(String arg0) throws CacheLoaderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Object arg0) throws CacheWriterException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(Entry<? extends String, ? extends ForexTrade> arg0) throws CacheWriterException {
		// TODO Auto-generated method stub
		
	}

}
