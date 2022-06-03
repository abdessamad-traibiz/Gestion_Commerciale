package com.emsi.security;
 

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emsi.imetier.IDossierMetier;
import com.emsi.imetier.IFournisseurMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class SecurityController 
{

	@Autowired private HttpSession session;
	@Autowired private IFournisseurMetier mf;
	@Autowired private IDossierMetier metierDos;
	
	
	@RequestMapping("/") public String index(Model model)
	{
		model.addAttribute("dossiers", metierDos.getDossiers());
		return "index";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) 
	{
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null) 
	    {    
	        new SecurityContextLogoutHandler().logout(request, response, auth); 
	    }
	    return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}
	

	@RequestMapping("/login") public String login(Principal p)
	{ 
		session.setAttribute("maSociete",mf.getFournisseur("CODE_0")); 
		return p==null?"login":"redirect:/" ; 
	} 
	
	@RequestMapping("/403") public String forbidden()
	{
		return "403";
	}
	
	@RequestMapping("/*") public String notfound()
	{
		return "404";
	}
}
