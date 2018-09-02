package my.snapshot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.snapshot.bean.ForexTrade;
import my.snapshot.mapper.ForexTradeMapper;
import my.snapshot.service.ForexTradeService;

@Service(value="ForexService")
public class ForexTradeServiceImpl implements ForexTradeService {

	@Autowired
	private ForexTradeMapper forexTradeMapper;
	@Override
	public List<ForexTrade> findAll() {
		// TODO Auto-generated method stub
		return forexTradeMapper.findAll();
	}

}
