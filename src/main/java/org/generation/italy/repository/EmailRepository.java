package org.generation.italy.repository;

import java.util.List;
import org.generation.italy.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer>{

	List<Email> findByIsBanned(Boolean paramentro);
	
	Email findAllById(Integer parametro);

	Email findByEmailContaining(String parametro);
}
