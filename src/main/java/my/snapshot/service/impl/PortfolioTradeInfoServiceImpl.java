package my.snapshot.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.cache.Cache.Entry;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;
import org.springframework.stereotype.Service;

import my.snapshot.constants.IgniteConstants;
import my.snapshot.ignite.IgniteManager;
import my.snapshot.ignite.model.UserTradeInfoKey;
import my.snapshot.ignite.model.UserTradeInfo;
import my.snapshot.service.PortfolioTradeInfoService;

@Service(value = "PortfolioTradeInfoService")
public class PortfolioTradeInfoServiceImpl implements PortfolioTradeInfoService {

	@Resource
	private IgniteManager igniteManager;
	
	private IgniteCache<UserTradeInfoKey, UserTradeInfo> tradeInfoCache;

	@Override
	public void init() {
		this.tradeInfoCache = igniteManager.getIgniteInstance().cache(IgniteConstants.CACHE_NAME_FOREX_TRADE);
	}
	@Override
	public void put(UserTradeInfo portfolioTradeInfo) {
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
	public List<UserTradeInfo> getTradeInfos() {
		// TODO Auto-generated method stub
		List<UserTradeInfo> tradeInfoList = new ArrayList<>();
		SqlQuery<UserTradeInfoKey, UserTradeInfo> sql = new SqlQuery<>(UserTradeInfo.class, "login!=?");
		try(QueryCursor<Entry<UserTradeInfoKey, UserTradeInfo>> cursor = tradeInfoCache.query(sql.setArgs(""))){
			for (Entry<UserTradeInfoKey, UserTradeInfo> e : cursor) {
				tradeInfoList.add(e.getValue());
			}
		}
		return tradeInfoList;
	}
	@Override
	public List<UserTradeInfo> getTradeInfos(String login) {
		// TODO Auto-generated method stub
		List<UserTradeInfo> tradeInfoList = new ArrayList<>();
		SqlQuery<UserTradeInfoKey, UserTradeInfo> sql = 
				new SqlQuery<>(UserTradeInfo.class, "login = ?");
		try(QueryCursor<Entry<UserTradeInfoKey, UserTradeInfo>> cursor = 
				tradeInfoCache.query(sql.setArgs(login))){
			for (Entry<UserTradeInfoKey, UserTradeInfo> e : cursor) {
				tradeInfoList.add(e.getValue());
			}
		}
		return tradeInfoList;
	}
	@Override
	public List<String> getSymbolsOfLogin(String login) {
		// TODO Auto-generated method stub
		List<String> symbolList = new ArrayList<>();
		SqlFieldsQuery sql = new SqlFieldsQuery("select distinct symbol from PortfolioTradeInfo where login = ?");
		try(QueryCursor<List<?>> cursor = tradeInfoCache.query(sql.setArgs(login))){
			for (List<?> e : cursor) {
				symbolList.add((String) e.get(0));
			}
		}
		return symbolList;
	}
	@Override
	public List<UserTradeInfo> getTradeInfos(String login, String symbol) {
		// TODO Auto-generated method stub
		List<UserTradeInfo> tradeInfoList = new ArrayList<>();
		SqlQuery<UserTradeInfoKey, UserTradeInfo> sql = 
				new SqlQuery<>(UserTradeInfo.class, "login=? and symbol=?");
		try(QueryCursor<Entry<UserTradeInfoKey, UserTradeInfo>> cursor = 
				tradeInfoCache.query(sql.setArgs(login, symbol))){
			for (Entry<UserTradeInfoKey, UserTradeInfo> e : cursor) {
				tradeInfoList.add(e.getValue());
			}
		}
		return tradeInfoList;
	}

}
