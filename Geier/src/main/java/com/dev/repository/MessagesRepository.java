package com.dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.model.Messages;

public interface MessagesRepository extends JpaRepository<Messages, Integer> {
	
	
	

}
