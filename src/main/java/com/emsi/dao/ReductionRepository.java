package com.emsi.dao;
  
import com.emsi.entities.Reduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReductionRepository extends JpaRepository<Reduction, Long>
{
}  

