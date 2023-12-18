package com.dev.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.model.Enviados;
import com.dev.repository.EnviadosRepository;
import com.dev.repository.MessagesRepository;

@Service
public class EnviadosService {
	

	
	@Autowired
	private EnviadosRepository enviadosRepository;

	
	
	
	
	
	public void save(Enviados enviado) {
		
	
		
		enviadosRepository.save(enviado);
	}
	
	public Enviados get(Integer id) {
		return enviadosRepository.findById(id).get();
	}
	
	public void delete(Integer id) {
		enviadosRepository.deleteById(id);
	}





	public List<Enviados> listAll() {
		
		
		return enviadosRepository.findAll();
	}





	
	
	
}
