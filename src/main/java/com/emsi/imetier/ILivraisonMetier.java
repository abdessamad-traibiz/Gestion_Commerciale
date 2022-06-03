package com.emsi.imetier;

import java.util.List;

import com.emsi.entities.Livraison;
import org.springframework.data.domain.Page;


public interface ILivraisonMetier 
{  
	public List<Livraison> getAllLivraisons();
	public Page<Livraison> getAllLivraisons(int page, int size ); 
	public Livraison getLivraison( Long id );   
	public Livraison saveLivraison(Livraison livraison);
	public boolean deleteLivraison(Long id);
}
