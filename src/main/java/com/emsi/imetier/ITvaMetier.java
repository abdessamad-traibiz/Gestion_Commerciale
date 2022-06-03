package com.emsi.imetier;

import java.util.List;

import com.emsi.entities.Tva;
import org.springframework.data.domain.Page;


public interface ITvaMetier 
{ 
	public List<Tva> getTvas();
	public Page<Tva> getTvas(int page, int size );
	public Page<Tva> getTvasByDesignation( String designation, int page, int size );
	public Tva getTva( Long code );   
	public Tva saveTva(Tva famille);
	public boolean deleteTva(Long code);
}
