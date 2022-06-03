package com.emsi.dao;
  
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.emsi.entities.ReglementFacture;


@Repository
public interface ReglementFactureRepository extends JpaRepository<ReglementFacture, Long>  
{
}  

