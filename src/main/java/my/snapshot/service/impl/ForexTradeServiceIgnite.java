package my.snapshot.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.cache.Cache.Entry;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import my.snapshot.bean.ForexTrade;
import my.snapshot.constants.IgniteConstants;
import my.snapshot.ignite.IgniteManager;
import my.snapshot.service.ForexTradeService;

@Service(value="ForexTradeServiceIgnite")
public class ForexTradeServiceIgnite implements ForexTradeService {

	@Resource
	private IgniteManager igniteManager;
	@Override
	public List<ForexTrade> findAll() {
		// TODO Auto-generated method stub
		System.out.println("Query Ignite.");
		Ignite ignite = igniteManager.getIgniteInstance();
		IgniteCache<String, ForexTrade> forexTradeCache = ignite.cache(IgniteConstants.CACHE_NAME_FOREX_TRADE);
		List<ForexTrade> forexTradeList = new ArrayList<>();
		SqlQuery<String, ForexTrade> sql = new SqlQuery<>(ForexTrade.class, "deal!=?");
		try(QueryCursor<Entry<String, ForexTrade>> cursor = forexTradeCache.query(sql.setArgs(""))){
			for (Entry<String, ForexTrade> e : cursor) {
				forexTradeList.add(e.getValue());
			}
		}
		return forexTradeList;
	}

}
