package com.emsi.dao;
  
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.emsi.entities.Reduction;


@Repository
public interface ReductionRepository extends JpaRepository<Reduction, Long>  
{
}  

