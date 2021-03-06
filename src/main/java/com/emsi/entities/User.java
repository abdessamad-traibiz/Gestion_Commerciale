package com.emsi.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class User implements Serializable
{
	@Id @NotNull @Size(min=4,max=100) private String username;
	
	@Size(min=4,max=100)
	private String password;
	
	@NotNull
	private Boolean active = false;
	   
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	private Collection<UsersRoles> roles;

	public User() {}
	
	

	public User(String username, String password, Boolean active) {
		super();
		this.username = username;
		this.password = password;
		this.active = active; 
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<UsersRoles> getRoles() {
		return roles;
	}

	public void setRoles(Collection<UsersRoles> roles) {
		this.roles = roles;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}  
	@Override
	public String toString() { return username; }
}