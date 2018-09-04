package my.snapshot.service;

import java.util.List;

import my.snapshot.bean.ForexTrade;

public interface ForexTradeService {
	public List<ForexTrade> findAll();

	public List<ForexTrade> find(int startIndex, int size);
}
