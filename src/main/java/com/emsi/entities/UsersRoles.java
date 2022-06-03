package com.emsi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UsersRoles 
{
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="username")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="ROLE_ID")
	private Role role;
	
	public UsersRoles() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	} 
}
