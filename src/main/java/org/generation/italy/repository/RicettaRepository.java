package org.generation.italy.repository;

import java.util.List;

import org.generation.italy.model.Ricetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RicettaRepository extends JpaRepository<Ricetta, Integer>{

	@Query(value="select * from ricetta r WHERE data_di_creazione >= DATE(NOW()) - INTERVAL 7 DAY order by data_di_creazione", nativeQuery = true)
	List<Ricetta> findLastSevenDays();
	
	List<Ricetta> findByTitoloContainingIgnoreCaseOrderByDataDiCreazione(String keyword);
	
	List<Ricetta> findByCategoria_idIs(Integer categoryId);
	
}
