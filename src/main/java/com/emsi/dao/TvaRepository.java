package com.emsi.dao;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emsi.entities.Tva;


@Repository
public interface TvaRepository extends JpaRepository<Tva, Long>  
{
}  

