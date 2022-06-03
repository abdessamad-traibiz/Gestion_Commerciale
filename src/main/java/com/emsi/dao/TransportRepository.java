package com.emsi.dao;
  
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.emsi.entities.Transport;


@Repository
public interface TransportRepository extends JpaRepository<Transport, Long>  
{
}  

