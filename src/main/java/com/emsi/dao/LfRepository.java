package com.emsi.dao;
   
import java.util.Date;

import com.emsi.entities.LigneFacture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LfRepository extends JpaRepository<LigneFacture, Long>
{
	@Query("select lf from LigneFacture lf where lf.facture.dateFacture between :d1 and :d2 "
			+ "group by lf.produit order by lf.qte desc")
	public Page<LigneFacture> findAllBetween(@Param("d1")Date d1,@Param("d2")Date d2, Pageable pageable);
} 
 



