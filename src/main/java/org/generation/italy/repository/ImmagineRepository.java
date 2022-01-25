package org.generation.italy.repository;

import org.generation.italy.model.Immagine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImmagineRepository extends JpaRepository<Immagine, Integer>{

}
