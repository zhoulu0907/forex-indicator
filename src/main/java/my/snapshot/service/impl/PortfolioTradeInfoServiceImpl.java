package my.snapshot.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.cache.Cache.Entry;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;
import org.springframework.stereotype.Service;

import my.snapshot.constants.IgniteConstants;
import my.snapshot.ignite.IgniteManager;
import my.snapshot.ignite.model.PortfolioKey;
import my.snapshot.ignite.model.PortfolioTradeInfo;
import my.snapshot.service.PortfolioTradeInfoService;

@Service(value = "PortfolioTradeInfoService")
public class PortfolioTradeInfoServiceImpl implements PortfolioTradeInfoService {

	@Resource
	private IgniteManager igniteManager;
	
	private IgniteCache<PortfolioKey, PortfolioTradeInfo> tradeInfoCache;

	@Override
	public void init() {
		this.tradeInfoCache = igniteManager.getIgniteInstance().cache(IgniteConstants.CACHE_NAME_FOREX_TRADE);
	}
	@Override
	public void put(PortfolioTradeInfo portfolioTradeInfo) {
		// TODO Auto-generated method stub
		tradeInfoCache.put(portfolioTradeInfo.getId(), portfolioTradeInfo);
	}

	@Override
	public List<String> getLogins() {
		// TODO Auto-generated method stub
		List<String> loginList = new ArrayList<String>();
		SqlFieldsQuery sql = new SqlFieldsQuery("select distinct login from PortfolioTradeInfo");
		
		try(QueryCursor<List<?>> cursor = tradeInfoCache.query(sql)){
			for (List<?> row : cursor) {
				loginList.add((String) row.get(0));
			}
		}
		return loginList;
	}
	@Override
	public List<PortfolioTradeInfo> getTradeInfos() {
		// TODO Auto-generated method stub
		List<PortfolioTradeInfo> tradeInfoList = new ArrayList<>();
		SqlQuery<PortfolioKey, PortfolioTradeInfo> sql = new SqlQuery<>(PortfolioTradeInfo.class, "login!=?");
		try(QueryCursor<Entry<PortfolioKey, PortfolioTradeInfo>> cursor = tradeInfoCache.query(sql.setArgs(""))){
			for (Entry<PortfolioKey, PortfolioTradeInfo> e : cursor) {
				tradeInfoList.add(e.getValue());
			}
		}
		return tradeInfoList;
	}

}
