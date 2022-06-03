package com.emsi.metier;

import java.util.List;

import javax.transaction.Transactional;

import com.emsi.dao.FournisseurRepository;
import com.emsi.entities.Fournisseur;
import com.emsi.imetier.IFournisseurMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class FournisseurMetierImpl implements IFournisseurMetier
{ 
	@Autowired
	private FournisseurRepository frsRep;

	@Override
	public List<Fournisseur> getFournisseurs() {
		// TODO Auto-generated method stub
		return frsRep.findAll();
	}
 

	@Override
	public Fournisseur getFournisseur(String code) {
		// TODO Auto-generated method stub
		try {
			return frsRep.findByCode(code); 
		} catch (Exception e) {return null;} 
	}

	@Override
	public Fournisseur saveFournisseur(Fournisseur frs) {
		// TODO Auto-generated method stub
		return frsRep.save(frs);
	}

	@Override
	public boolean deleteFournisseur(String code) {
		// TODO Auto-generated method stub
		Fournisseur frs = frsRep.findByCode(code);
		if(frs!=null) {
			frsRep.delete(frs);
			return true;
		}
		return false;
	}


	@Override
	public Page<Fournisseur> getFournisseursByMotCle(String mc, int page, int size) {
		// TODO Auto-generated method stub
		return frsRep.findAllByMotCle(mc, new PageRequest(page, size));
	}
 
}
