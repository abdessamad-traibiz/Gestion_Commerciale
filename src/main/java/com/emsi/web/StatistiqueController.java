package com.emsi.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.emsi.entities.LigneFacture;
import com.emsi.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emsi.imetier.ICommandeMetier;
import com.emsi.imetier.IDossierMetier;
import com.emsi.imetier.IFactureMetier;
import com.emsi.imetier.IFamilleMetier;
import com.emsi.imetier.ILfMetier;
import com.emsi.imetier.IProduitMetier;


@Controller
public class StatistiqueController 
{   
	@Autowired private IFactureMetier metierFacture;
	@Autowired private ILfMetier metierLf;
		
	@Autowired private IProduitMetier metierProduit;
	@Autowired private IDossierMetier metierDos;
	@Autowired private IFamilleMetier metierFamille;

	@Autowired private ICommandeMetier metierCmd;  
		 
		 
	@RequestMapping("/statistiques") public String index(
			Model model,
			@RequestParam(name="p",defaultValue="0")int p,
			@RequestParam(name="s",defaultValue="8")int s,
			@RequestParam(name="mc",defaultValue="")String mc
	){ 
		model.addAttribute("dossiers", metierDos.getDossiers());
		model.addAttribute("categories", metierFamille.getFamilles() );
		model.addAttribute("produits", metierProduit.getProduits());
		
		return "rapports";
	} 
	
	@RequestMapping(value="/getstatistiques", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody HashMap<String,List<Statistique>> getStatistiques( 
			@RequestParam(name="d",defaultValue="0")Long numDossier,
			@RequestParam(name="j",defaultValue="0")Integer j,
			@RequestParam(name="m",defaultValue="0")Integer m,
			@RequestParam(name="a",defaultValue="0")Integer a,
			@RequestParam(name="p",defaultValue="")String p
	)
	{
		HashMap<String,List<Statistique>> sts = new HashMap<>(); 
		sts.put("sachats", new ArrayList<>());
		sts.put("sventes", new ArrayList<>());
		sts.put("sprixachats", new ArrayList<>());
		sts.put("sprixventes", new ArrayList<>()); 
		sts.put("produitsAchete", new ArrayList<>());
		sts.put("produitsVendu", new ArrayList<>());
		
		String[] ms = {"Janvier", "Fevrier", "Mars", "Avril", "May", "Juin", "Juillet", "Aout", "September", "Octob", "November", "December"};
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
  
		if(a!=0 && m==0) {
			for(int i=1;i<=12;i++)  
			try{ 
				Date d1 = sf.parse(a+"-"+(i)+"-1"), d2 = sf.parse(a+"-"+(i+1)+"-1");
				regler2(numDossier, d1, d2, p, sts, ms[i-1]);
			}
			catch(Exception e){System.err.println("nooo");}
  
			try{  
				Date d1 = sf.parse(a+"-1-1"), d2 = sf.parse((a+1)+"-1-1");
				regler(sts.get("produitsAchete"),metierFacture.getSProduitsAchete(numDossier ,d1,d2));
				regler(sts.get("produitsVendu"),metierFacture.getSProduitsVendu(numDossier ,d1,d2));
			} catch(ParseException e){} 
		}
		else if(a!=0){
			if(j!=0) {
				try{
					Date d1 = sf.parse(a+"-"+m+"-"+j);  
					regler2(numDossier, d1, d1, p, sts, j+"");
				}catch (Exception e) {};
			}
			else for(int i=1;i<=31;i++)  
			try{  
				Date d1 = sf.parse(a+"-"+m+"-"+i) ,d2 = sf.parse(a+"-"+(m)+"-"+i);  
				regler2(numDossier, d1, d2, p, sts, i+"");
			}
			catch(Exception e){ }  

			try{ 
				Date d1 = sf.parse(a+"-"+m+"-1") ,d2 = sf.parse(a+"-"+(m+1)+"-1");  
				regler(sts.get("produitsAchete"),metierFacture.getSProduitsAchete(numDossier ,d1,d2));
				regler(sts.get("produitsVendu"),metierFacture.getSProduitsVendu(numDossier ,d1,d2));
			} catch(ParseException e){}
		} 
		
		return sts;
	}
	
	
	 
	
	private void regler2(Long numDossier, Date d1, Date d2, String p,
			HashMap<String, List<Statistique>>listes, String label)
	{  
		Integer totalv=0, totala=0; Double totalpa=0d,totalpv=0d;
		if(p.isEmpty()){
			totala  = metierFacture.getSAchats(numDossier,d1,d2);
			totalpa = metierFacture.getSPrixAchats(numDossier,d1,d2);
			totalv  = metierFacture.getSVentes(numDossier,d1,d2);
			totalpv = metierFacture.getSPrixVentes(numDossier,d1,d2);
		}
		else{ 
			totala  = metierFacture.getSAchatsProduit(numDossier,d1,d2,p);  
			totalpa = metierFacture.getSPrixAchatsProduit(numDossier,d1,d2,p);  
			totalv  = metierFacture.getSVenteProduit(numDossier,d1,d2,p); 
			totalpv = metierFacture.getSPrixVenteProduit(numDossier,d1,d2,p); 
		}
		listes.get("sventes") 		.add(new Statistique(label, totalv+"")); 
		listes.get("sprixventes")	.add(new Statistique(label, totalpv+"")); 
		listes.get("sachats")		.add(new Statistique(label, totala+"")); 
		listes.get("sprixachats")	.add(new Statistique(label, totalpa+""));
	}
	
	private void regler(List<Statistique>list,List<Object[]> total)
	{ 
		for(int x=0;x<total.size();x++) {  
			Object[] objs = total.get(x);
			Produit prd = (Produit)objs[0];
			Object nbr = objs[1]; 
			list.add(new Statistique(prd+"" , nbr+""));
		}
	} 
	
	
	@RequestMapping(value="/getglobalstatistiques", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody HashMap<String,Object> getGlobalStatistiques(  
			@RequestParam(name="m",defaultValue="0")Integer m,
			@RequestParam(name="a",defaultValue="0")Integer a 
	)
	{ 
		HashMap<String,Object> statistiques = new HashMap<>(); 

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); 
		Date d1=null,d2=null,d3=null,d4=null;
		try{ 
			d1 = sf.parse(a+"-"+(m-1)+"-1"); d2 = sf.parse(a+"-"+(m)+"-1");  
			d3 = sf.parse(a+"-"+(m)+"-1"); d4 = sf.parse(a+"-"+(m+1)+"-1"); 
		}
		catch(Exception e){}
		
		//commandes
		Integer nCmdLast = metierCmd.getNombreCommandes(d1,d2);
		Integer nCmd     = metierCmd.getNombreCommandes(d3,d4);  
		Double res = nCmdLast!=0?((nCmdLast>nCmd)?
				(-( (nCmdLast-nCmd)/(double)nCmdLast)*100):
				(( (nCmd-nCmdLast)/(double)nCmdLast)*100)):0 ;
		statistiques.put("nbrCmd", nCmd);  
		statistiques.put("resCmd", res);

		//Qte vendu 
		nCmdLast = metierFacture.getQteVentes(d1,d2);
		nCmd     = metierFacture.getQteVentes(d3,d4);   
		res = nCmdLast!=0?((nCmdLast>nCmd)?
				(-( (nCmdLast-nCmd)/(double)nCmdLast)*100):
				(( (nCmd-nCmdLast)/(double)nCmdLast)*100) ):0;
		statistiques.put("nbrQte", nCmd);  
		statistiques.put("resQte", res);

		//revenue 
		Double revLast = metierFacture.getRevenues(d1,d2);
		Double rev     = metierFacture.getRevenues(d3,d4);  
		res = revLast!=0?((revLast>rev)?
				(-( (revLast-rev)/(double)revLast)*100):
				(( (rev-revLast)/(double)revLast)*100) ):0;
		statistiques.put("nbrRev", rev);  
		statistiques.put("resRev", res);
		
		//croissance 
		Date d_1=null, d_2=null;
		try{ 
			d_1 = sf.parse(a+"-"+(m-2)+"-1"); d_2 = sf.parse(a+"-"+(m-1)+"-1"); 
			Double lastCro = revLast - metierFacture.getRevenues(d_1,d_2);
			Double actuCro = rev - revLast;
			res = lastCro!=0?((lastCro>actuCro)?
					(-( (lastCro-actuCro)/(double)lastCro)*100):
					(( (actuCro-lastCro)/(double)lastCro)*100) ):0;
			statistiques.put("nbrCro", actuCro);  
			statistiques.put("resCro", res);
		} 
		catch(Exception e){} 
		return statistiques;
	}
	
	
	@RequestMapping(value="/getStatistiquesAV", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody HashMap<String,List> getStatistiquesAV( 
			@RequestParam(name="m",defaultValue="0")Integer m,
			@RequestParam(name="a",defaultValue="0")Integer a 
	) 
	{
		HashMap<String,List> sts = new HashMap<>();  
		sts.put("labels", new ArrayList<Label>()); 
		sts.put("achats", new ArrayList<Value>());
		sts.put("ventes", new ArrayList<Value>()); 
		sts.put("revenus", new ArrayList<Value>()); 
		
		String[] ms = {"Janvier", "Fevrier", "Mars", "Avril", "May", "Juin", "Juillet", "Aout", "September", "Octob", "November", "December"};
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
  
		Date d1,d2;
			for(int i=1;i<=12;i++)  
			try{ 
				d1= sf.parse(a+"-"+(i)+"-1"); d2 = sf.parse(a+"-"+(i+1)+"-1");

				//  = metierFacture.getRevenues(d1,d2);
				Double totalv   = metierFacture.getPrixVentes(d1,d2);
				Double totala   = metierFacture.getPrixAchats(d1,d2); 
				
				sts.get("labels").add(  new Label(ms[i-1]));
				sts.get("revenus").add( new Value((totalv-totala)+"") ); 
				sts.get("achats").add(  new Value(totala+"") ); 
				sts.get("ventes").add(  new Value(totalv+"") ); 
			}
			catch(Exception e){System.err.println("nooo"); e.printStackTrace();}
    
		return sts;
	}
	
	@RequestMapping(value="/getmostselled", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody HashMap<String,List<String>> getMostSelled( 
			@RequestParam(name="m",defaultValue="0")Integer m,
			@RequestParam(name="a",defaultValue="0")Integer a 
	) 
	{
		HashMap<String,List<String>> lfs= new HashMap<String,List<String>>(); 
		lfs.put("produits", new ArrayList<>()); 
		lfs.put("prixs", new ArrayList<>()); 
		lfs.put("qtes", new ArrayList<>()); 
		lfs.put("ttcs", new ArrayList<>()); 
		
		try{ 
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); 
			Date d1 = sf.parse(a+"-"+(m)+"-1"), d2 = sf.parse(a+"-"+(m+1)+"-1");  
			List<LigneFacture> list = metierLf.getLignesFacture(d1,d2).getContent();
			for(LigneFacture lf : list) {
				lfs.get("produits").add(lf.getProduit().getDesignation());
				lfs.get("prixs").add(lf.getPrix()+"");
				lfs.get("qtes").add(lf.getQte()+"");
				lfs.get("ttcs").add(lf.getTtc()+""); 
			}
		}
		catch(Exception e){ System.err.println("nooo"); } 

		return lfs;
	}
	
	public class Label { public Label(String v){label=v;} public String label; }  
	public class Value { public Value(String v){value=v;} public String value; }  
	public class Statistique  { public String label,value; public Statistique(String l, String v){label=l;value=v;} }
}











