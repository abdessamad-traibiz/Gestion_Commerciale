package com.emsi.dao;
 
import com.emsi.entities.Tva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TvaRepository extends JpaRepository<Tva, Long>
{
}  

