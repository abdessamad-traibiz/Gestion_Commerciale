package com.emsi.dao;
  
import com.emsi.entities.ReductionFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReductionFactureRepository extends JpaRepository<ReductionFacture, Long>
{
}  

