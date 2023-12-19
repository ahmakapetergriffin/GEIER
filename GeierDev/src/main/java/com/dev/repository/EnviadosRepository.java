package com.dev.repository;



import org.springframework.data.jpa.repository.JpaRepository;



import com.dev.model.Messages;

public interface EnviadosRepository extends JpaRepository<Messages, Integer> {
	


}
