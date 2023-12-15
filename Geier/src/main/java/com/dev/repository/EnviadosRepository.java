package com.dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.model.Enviados;

public interface EnviadosRepository extends JpaRepository<Enviados, Integer> {
	
	
	@Query("SELECT m FROM Messages m WHERE m.sentby LIKE %?1%")
    public List<Enviados> getSentBy(String sentby);
	

}
