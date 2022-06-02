package com.emsi.metier;

import java.util.List;

import javax.transaction.Transactional;

import com.emsi.imetier.IReductionMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.emsi.dao.ReductionRepository;
import com.emsi.entities.Reduction;


@Service
@Transactional
public class ReductionMetierImpl implements IReductionMetier
{ 
	@Autowired
	private ReductionRepository regRep;

	@Override
	public List<Reduction> getReductions() {
		// TODO Auto-generated method stub
		return regRep.findAll();
	}

	@Override
	public Page<Reduction> getReductions(int page, int size) {
		// TODO Auto-generated method stub
		return regRep.findAll(new PageRequest(page, size));
	}

	@Override
	public Reduction getReduction(Long id) {
		// TODO Auto-generated method stub
		return regRep.getOne(id);
	}

	@Override
	public Reduction saveReduction(Reduction reglement) {
		// TODO Auto-generated method stub
		return regRep.save(reglement);
	}

	@Override
	public boolean deleteReduction(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
 
 
}
