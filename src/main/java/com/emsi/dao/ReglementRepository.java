package com.emsi.dao;
  
import com.emsi.entities.Reglement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReglementRepository extends JpaRepository<Reglement, Long>
{
}  

