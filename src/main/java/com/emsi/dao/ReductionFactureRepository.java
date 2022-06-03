package com.emsi.dao;
  
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.emsi.entities.ReductionFacture;


@Repository
public interface ReductionFactureRepository extends JpaRepository<ReductionFacture, Long>  
{
}  

