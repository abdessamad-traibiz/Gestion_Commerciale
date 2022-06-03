package com.emsi.metier;

import javax.transaction.Transactional;

import com.emsi.imetier.IRGFMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emsi.dao.ReglementFactureRepository;
import com.emsi.entities.ReglementFacture;


@Service
@Transactional
public class RGFMetierImpl implements IRGFMetier
{ 
	@Autowired
	private ReglementFactureRepository regRep;
 
	
	@Override
	public ReglementFacture getReglementFacture(Long id) {
		// TODO Auto-generated method stub
		return regRep.getOne(id);
	}

	@Override
	public ReglementFacture saveReglementFacture(ReglementFacture reglementFacture) {
		// TODO Auto-generated method stub
		return regRep.save(reglementFacture);
	}

	@Override
	public boolean deleteReglementFacture(Long id) {
		// TODO Auto-generated method stub
		ReglementFacture rgf = regRep.getOne(id);
		if(rgf==null) return false;
		regRep.delete(rgf);
		return true;
	}
 
 
}
