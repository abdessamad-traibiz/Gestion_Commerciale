package com.emsi.metier;

import java.util.List;

import javax.transaction.Transactional;

import com.emsi.entities.Stock;
import com.emsi.imetier.IStockMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.emsi.dao.StockRepository;


@Service
@Transactional
public class StockMetierImpl implements IStockMetier
{ 
	@Autowired private StockRepository sRep;

	@Override
	public List<Object[]> getMoyenStocksAnne(String refp, Integer y) { 
		return sRep.getMoyenStocksAnne(refp, y);
	}

	@Override
	public List<Object[]> getMoyenStocksMois(String refp, Integer y,Integer m) {
		return sRep.getMoyenStocksMois(refp,  y,m);
	}

	@Override
	public List<Object[]> getMoyenStocksJour(String refp, Integer y,Integer m,Integer j) {
		return sRep.getMoyenStocksJour(refp, y,m,j);
	}

	@Override
	public void saveStock(Stock stock) {
		sRep.save(stock);
	}

	@Override
	public List<Object[]> getMoyenStocksJour(Integer a, Integer m, Integer j) {
		return sRep.getMoyenStocksJour(a,m,j);
	}

	@Override
	public List<Object[]> getMoyenStocksMois(Integer a, Integer m) {
		return sRep.getMoyenStocksMois(a,m);
	}

	@Override
	public List<Object[]> getMoyenStocksAnne(Integer a) {
		return sRep.getMoyenStocksAnne(a);
	} 
 
}
