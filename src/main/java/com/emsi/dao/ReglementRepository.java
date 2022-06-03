package com.emsi.dao;
  
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.emsi.entities.Reglement;


@Repository
public interface ReglementRepository extends JpaRepository<Reglement, Long>  
{
}  

