package com.emsi.dao;
   
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.emsi.entities.Role;
import com.emsi.entities.UsersRoles;
 

@Repository
public interface UserRolesRepository extends JpaRepository<UsersRoles, String>  
{ 
	@Query("select ur from UsersRoles ur where ur.role.id = :r and ur.user.username = :u")
	public Role findByRoleAndUser( @Param("r")Long idRole,@Param("u")String username  );   
}  









