package com.emsi.imetier;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.emsi.entities.LigneFacture;
 

public interface ILfMetier 
{   
	public LigneFacture getLigneFacture( Long id );   
	public LigneFacture saveLigneFacture(LigneFacture ligneFacture);
	public boolean deleteLigneFacture(Long id);
	public Page<LigneFacture> getLignesFacture(Date d1, Date d2);
}
