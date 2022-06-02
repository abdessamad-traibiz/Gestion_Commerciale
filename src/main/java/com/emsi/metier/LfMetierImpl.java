package com.emsi.metier;

import java.util.Date;

import javax.transaction.Transactional;

import com.emsi.imetier.ILfMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.emsi.dao.LfRepository;
import com.emsi.entities.LigneFacture;


@Service
@Transactional
public class LfMetierImpl implements ILfMetier
{ 
	@Autowired
	private LfRepository lnfRep;

	@Override
	public LigneFacture getLigneFacture(Long id) {
		// TODO Auto-generated method stub
		return lnfRep.getOne(id);
	}

	@Override
	public LigneFacture saveLigneFacture(LigneFacture lc) {
		// TODO Auto-generated method stub
		return lnfRep.save(lc);
	}

	@Override
	public boolean deleteLigneFacture(Long id) {
		// TODO Auto-generated method stub
		LigneFacture lc = lnfRep.getOne(id);
		if(lc==null) return false;
		lnfRep.delete(lc);
		return true;
	}

	@Override
	public Page<LigneFacture> getLignesFacture(Date d1, Date d2) {
		return lnfRep.findAllBetween(d1,d2,new PageRequest(0, 10));
	}
 
 
 
}
