package com.emsi.dao;
  
import com.emsi.entities.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Long>
{
}  

