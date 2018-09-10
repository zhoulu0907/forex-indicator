package my.snapshot.mapper;

import java.util.List;


import my.snapshot.bean.ForexTrade;

public interface ForexTradeMapper {
	public List<ForexTrade> findAll();

	public List<ForexTrade> find(Integer startIndex, Integer size);
}
