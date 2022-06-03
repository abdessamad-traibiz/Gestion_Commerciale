package com.emsi.web;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.emsi.dao.RoleRepository;
import com.emsi.dao.UserRepository;
import com.emsi.dao.UserRolesRepository;
import com.emsi.entities.Role;
import com.emsi.entities.User;
import com.emsi.entities.UsersRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 
public class UserController 
{
	@Autowired private UserRepository usrRep;
	@Autowired private RoleRepository rolRep;
	@Autowired private UserRolesRepository urlRep;
	@Autowired private HttpSession session;
	
	@RequestMapping("/users") public String index(
			Model model,
			@RequestParam(name="p",defaultValue="0")int p,
			@RequestParam(name="s",defaultValue="8")int s,
			@RequestParam(name="mc",defaultValue="")String mc
	){
		Page<User> users = usrRep.findAll(new PageRequest(p, s));
		model.addAttribute("users",users.getContent());
		  
		model.addAttribute("pages", new int[users.getTotalPages()]);
		model.addAttribute("size", s);
		model.addAttribute("pageCourant", p);
		model.addAttribute("mc", mc); 

		if(model.asMap().get("user")==null)
			model.addAttribute("user", new User());
		model.addAttribute("roles", rolRep.findAll());

		HashMap<String, String> userRoles = (HashMap<String, String>) session.getAttribute("userRoles");
		if(userRoles!=null) model.addAttribute("rolesUser", userRoles.values());
		
		return "users";
	} 

	@RequestMapping(value= {"/users/add"}, method=RequestMethod.POST)
	public String adduser(@Valid User user, BindingResult result, Model model) 
	{      
		if( usrRep.findByUserName(user.getUsername()) !=null ) 
			result.rejectValue("username", "error.user", "Nom d'utilisateur déja exist !.");
		
		if(saveuser(user,result,model)) 
			model.addAttribute("addOk","user ajouté !");
		else model.addAttribute("addFailed",true);  
		
		return index(model,0,8,"");
	}
	
	
	@RequestMapping(value="/users/update",method=RequestMethod.POST)
	public String updateuser(@Valid User user, BindingResult result, Model model) 
	{    
		if(saveuser(user,result,model)) 
			model.addAttribute("updateOk","user "+user.getUsername()+" est Mis à jour!");
		else model.addAttribute("updateFailed",true);
		
		return index(model,0,8,"");
	} 
	
	private boolean saveuser(User user, BindingResult result, Model model)
	{
		HashMap<String, Role> userRoles = (HashMap<String, Role>) session.getAttribute("userRoles");
		if(userRoles==null || userRoles.size()==0)
			result.rejectValue("roles", "error.user", "Vous devez affecter au moins un role !.");
		  
		if (result.hasErrors()) 
		{ 
			model.addAttribute("user", user);
			return false;
		} 
		  
		user.setPassword( new BCryptPasswordEncoder().encode(user.getPassword()) );
		usrRep.save(user);
		 
		if(user.getRoles()!=null) {
			for(UsersRoles ur : user.getRoles())
				if(userRoles.get(ur.getRole().getRole())==null) 
					urlRep.delete(ur);  
		}
		user.setRoles( new ArrayList<>() );
		
		for(Role r : userRoles.values())
		{
			UsersRoles ur = new UsersRoles();
			ur.setUser(user);
			ur.setRole(r);
			ur = urlRep.save(ur);
			user.getRoles().add(ur);
		}
		usrRep.save(user);
		return true;
	}
	
	@RequestMapping(value="/users/delete")
	public String deleteuser(Model model,@RequestParam(name="username",defaultValue="0")String username) 
	{  
		usrRep.delete( usrRep.getOne(username) );
		model.addAttribute("deleteOk","user "+username+" est supprimé");
		return index(model,0,8,"");
	}
	
	@RequestMapping(value="/users/get", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody HashMap getuser(@RequestParam(name="username")String username) 
	{    
		User u = usrRep.findByUserName(username);  
		if(u!=null) {
			HashMap<String, String> hmu = new HashMap<String, String>() {{
				put("username", u.getUsername());
				put("active", u.getActive().toString());
			}};
			return hmu;
		} 
		return null;
	}
	
	@RequestMapping(value="/users/storeuserrole", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody String[] storeuserrole(@RequestParam(name="role")String r) 
	{     
		Role role = rolRep.findByRole(r);  
		if(role!=null) {
			HashMap<String, Role> userRoles = (HashMap<String, Role>) session.getAttribute("userRoles");
			if(userRoles == null) userRoles = new HashMap<>();
			userRoles.put(role.getRole(), role);
			System.err.println(userRoles);
			session.setAttribute("userRoles", userRoles);
			return new String[] {};
		} 
		return new String[] {"Role n'existe pas !"};
	} 
	
	@RequestMapping(value="/users/removeuserrole", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody HashMap removeuserrole(@RequestParam(name="role", defaultValue="")String r,
			@RequestParam(name="id", defaultValue="")String id) 
	{    
		HashMap<String, Role> userRoles = (HashMap<String, Role>) session.getAttribute("userRoles");
		if(userRoles != null) {
			try {
				if(!r.isEmpty())userRoles.remove(r);
				if(!id.isEmpty())userRoles.remove(rolRep.findByIdRole(Long.valueOf(id)).getRole());
			} 
			catch (Exception e) {}  
		} 
		return new HashMap();
	} 
	
	@RequestMapping(value="/users/updaterole", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody String[] updaterole(@RequestParam(name="id")Long id,@RequestParam(name="newrole")String nr) 
	{    
		if(nr.length()<=3) return new String[] {"Le role doit etre au moins 4c."};
		if(rolRep.findByRole(nr)!=null) return new String[] {"Le role déja exist !"};
		Role r = rolRep.findByIdRole(id); 
		if(r==null)return new String[] {"Le role n'existe pas !"};
		r.setRole(nr);
		rolRep.save(r);
		return new String[] {};
	}
	
	@RequestMapping(value="/users/addrole", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody String[] addrole(@RequestParam(name="role")String r) 
	{      
		if(r.length()<=3) return new String[] {"Le role doit etre au moins 4c."};
		if(rolRep.findByRole(r)!=null) return new String[] {"Le role déja exist !"};
		Role ro = rolRep.save(new Role(r)); 
		return new String[] {"",ro.getId()+""};
	}
	
	@RequestMapping(value="/users/deleterole", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody String[] addrole(@RequestParam(name="id")Long id) 
	{       
		rolRep.delete(new Role(id,"")); 
		return new String[] {};
	}
}
