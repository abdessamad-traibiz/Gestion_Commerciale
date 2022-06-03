package com.emsi.web;
 

import javax.validation.Valid;

import com.emsi.imetier.IFamilleMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emsi.entities.Famille;


@Controller
public class FamilleController  
{
	@Autowired private IFamilleMetier metierFamille;
	
	@RequestMapping(value= {"/savefamille"}, method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody Famille saveFamille(@Valid Famille famille) 
	{    	  
		metierFamille.saveFamille(famille);  
		return famille; 
	} 
 
	@RequestMapping(value="/deletefamille", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody Boolean deleteFamille(@RequestParam(name="code",defaultValue="0")Long code) 
	{  
		if(metierFamille.deleteFamille(code)) return true;  
		return false;
	}
	
	@RequestMapping(value="/getfamille", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody Famille getroduit(@RequestParam(name="code")Long code) 
	{  
		Famille famille = metierFamille.getFamille(code); 
		return famille;
	}
	
}
















