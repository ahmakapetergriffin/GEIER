package com.dev.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.model.Messages;
import com.dev.repository.MessagesRepository;

@Service
public class MessagesService {
	

	
	@Autowired
	private MessagesRepository messagesRepository;

	public List<Messages> listAll() {
		
	
		return messagesRepository.findAll();
		
	}
	
	
	
	
	
	public void save(Messages message) {
		
	
		
		messagesRepository.save(message);
	}
	
	public Messages get(Integer id) {
		return messagesRepository.findById(id).get();
	}
	
	public void delete(Integer id) {
		messagesRepository.deleteById(id);
	}













	
	
	
}
