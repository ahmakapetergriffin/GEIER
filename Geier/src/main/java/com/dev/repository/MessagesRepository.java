package com.dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.model.Messages;

public interface MessagesRepository extends JpaRepository<Messages, Integer> {
	
	
	@Query("SELECT m FROM Messages m WHERE m.username LIKE %?1%")
    public List<Messages> getUserName(String username);
	
	

}
