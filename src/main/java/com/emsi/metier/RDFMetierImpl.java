package com.emsi.metier;

import javax.transaction.Transactional;

import com.emsi.dao.ReductionFactureRepository;
import com.emsi.entities.ReductionFacture;
import com.emsi.imetier.IRDFMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class RDFMetierImpl implements IRDFMetier
{ 
	@Autowired
	private ReductionFactureRepository rdfRep;

	@Override
	public ReductionFacture getReductionFacture(Long id) {
		// TODO Auto-generated method stub
		return rdfRep.getOne(id);
	}

	@Override
	public ReductionFacture saveReductionFacture(ReductionFacture reglementFacture) {
		// TODO Auto-generated method stub
		return rdfRep.save(reglementFacture);
	}

	@Override
	public boolean deleteReductionFacture(Long id) {
		// TODO Auto-generated method stub
		ReductionFacture rdf = rdfRep.getOne(id);
		if(rdf==null) return false;
		rdfRep.delete(rdf);
		return true;
	} 
 
}
