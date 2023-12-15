package com.dev.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dev.model.Enviados;
import com.dev.model.Messages;
import com.dev.model.User;
import com.dev.service.EnviadosService;
import com.dev.service.MessagesService;
import com.dev.service.UserServices;





@Controller
public class AppController {

	@Autowired
	private UserServices userService;
	
	@Autowired
	private MessagesService messageService;
	
	@Autowired
	private EnviadosService enviadosService;
	
	
	

	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	
	
	
	
	
	//LOGIN - USUARIO
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user, HttpServletRequest request) 
			throws UnsupportedEncodingException, MessagingException {
		userService.register(user, getSiteURL(request));		
		return "register_success";
	}
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userService.listAll();
		model.addAttribute("listUsers", listUsers);
		
		return "users";
	}
	
	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}	
	
	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code) {
		if (userService.verify(code)) {
			return "verify_success";
		} else {
			return "verify_fail";
		}
	}
	
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable("id") Long id, Model model) {
		User user = userService.get(id);
		model.addAttribute("user",user);
		return "user_form";
	}
	
	
	//LOGIN - USUARIO
	
	
	//MENSAJES
		@GetMapping("/messages")
		public String listMessages(Model model,@Param("username") String username) {
			
			List<Messages> listMessages = messageService.listAll(username);
			model.addAttribute("listMessages", listMessages);
			model.addAttribute("username", username);

			return "messages";
		}
		
		@RequestMapping("/newMessages")
		public String showNewMessageForm(Model model) {
			Messages message = new Messages();
			model.addAttribute("message", message);
			
			return "new_message";
		}
		
		@RequestMapping(value = "/save1", method = RequestMethod.POST)
		public String saveMessage(@ModelAttribute("message") Messages message,@ModelAttribute("enviado") Enviados enviado) {
			messageService.save(message);
			enviadosService.save(enviado);
			
			return "redirect:/";
		}
		
		
		@RequestMapping("/edit1/{id}")
		public ModelAndView showMessageForm(@PathVariable(name = "id") Integer id) {
			ModelAndView mav = new ModelAndView("ver_message");
			
			Messages message = messageService.get(id);
			mav.addObject("message", message);
			
			return mav;
		}
		
		@RequestMapping("/delete1/{id}")
		public String deleteMessage(@PathVariable(name = "id") Integer id) {
			messageService.delete(id);
			
			return "messages";
		}
		

		//responder
		



		
		@GetMapping("/enviados")
		public String listEnviados(Model model,@Param("sentby") String sentby) {
			
			List<Enviados> listMessages = enviadosService.listAll(sentby);
			model.addAttribute("listMessages", listMessages);
			model.addAttribute("sentby", sentby);

			return "enviados";
		}


		@RequestMapping("/ver_enviado/{id}")
		public ModelAndView showEnviadoForm(@PathVariable(name = "id") Integer id) {
			ModelAndView mav = new ModelAndView("ver_enviado");
			
			Enviados enviado = enviadosService.get(id);
			mav.addObject("enviado", enviado);
			
			return mav;
		}
		
		
		@RequestMapping("/borrarenviado/{id}")
		public String deleteEnviado(@PathVariable(name = "id") Integer id) {
			enviadosService.delete(id);
			
			return "enviados";
		}
		
		
		// MENSAJES
		
	
	
	
}
