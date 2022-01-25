package org.generation.italy.repository;

import org.generation.italy.model.Commento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentoRepository extends JpaRepository<Commento, Integer>{

}
