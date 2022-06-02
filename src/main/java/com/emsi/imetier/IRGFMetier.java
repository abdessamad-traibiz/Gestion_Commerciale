package com.emsi.imetier;
 

import com.emsi.entities.ReglementFacture;


public interface IRGFMetier 
{   
	public ReglementFacture getReglementFacture(Long id );
	public ReglementFacture saveReglementFacture(ReglementFacture reglementFacture);
	public boolean deleteReglementFacture(Long id);
}
