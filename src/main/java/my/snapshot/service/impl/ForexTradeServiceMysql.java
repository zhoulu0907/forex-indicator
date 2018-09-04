package my.snapshot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.snapshot.bean.ForexTrade;
import my.snapshot.mapper.ForexTradeMapper;
import my.snapshot.service.ForexTradeService;

@Service(value="ForexTradeServiceMysql")
public class ForexTradeServiceMysql implements ForexTradeService {

	@Autowired
	private ForexTradeMapper forexTradeMapper;
	@Override
	public List<ForexTrade> findAll() {
		// TODO Auto-generated method stub
		System.out.println("Query Mysql.");
		return forexTradeMapper.findAll();
	}
	@Override
	public List<ForexTrade> find(int startIndex, int size) {
		// TODO Auto-generated method stub
		System.out.println("Query Mysql.");
		return forexTradeMapper.find(startIndex, size);
	}

}
