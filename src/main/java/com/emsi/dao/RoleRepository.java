package com.emsi.dao;
   
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.emsi.entities.Role;
 

@Repository
public interface RoleRepository extends JpaRepository<Role, String>  
{ 
	@Query("select u from Role u where u.role = :x")
	public Role findByRole( @Param("x")String role );  

	@Query("select u from Role u where u.id = :x")
	public Role findByIdRole( @Param("x")Long id );  
}  









