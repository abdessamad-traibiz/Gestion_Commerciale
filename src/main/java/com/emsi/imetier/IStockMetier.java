package com.emsi.imetier;
 
import java.util.List;

import com.emsi.entities.Stock;

public interface IStockMetier 
{  
	public List<Object[]> getMoyenStocksAnne(String refp, Integer y); 
	public List<Object[]> getMoyenStocksMois(String refp, Integer y,Integer m); 
	public List<Object[]> getMoyenStocksJour(String refp, Integer y,Integer m,Integer j);
	public void saveStock(Stock stock);
	public List<Object[]> getMoyenStocksJour(Integer a, Integer m, Integer j);
	public List<Object[]> getMoyenStocksMois(Integer a, Integer m);
	public List<Object[]> getMoyenStocksAnne(Integer a); 
}
