package com.emsi.imetier;

import java.util.List;

import org.springframework.data.domain.Page;

import com.emsi.entities.Reglement;
 

public interface IReglementMetier 
{ 
	public List<Reglement> getReglements();
	public Page<Reglement> getReglements(int page, int size ); 
	public Reglement getReglement( Long id );   
	public Reglement saveReglement(Reglement reglement);
	public boolean deleteReglement(Long id);
}
