package com.emsi.dao;
  
import com.emsi.entities.ReglementFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReglementFactureRepository extends JpaRepository<ReglementFacture, Long>
{
}  

