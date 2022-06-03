package com.emsi.imetier;

import java.util.List;

import com.emsi.entities.Reduction;
import org.springframework.data.domain.Page;


public interface IReductionMetier
{ 
	public List<Reduction> getReductions();
	public Page<Reduction> getReductions(int page, int size ); 
	public Reduction getReduction( Long id );   
	public Reduction saveReduction(Reduction reduction);
	public boolean deleteReduction(Long id);
}
