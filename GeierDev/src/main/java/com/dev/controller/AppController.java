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
import com.dev.model.Product;
import com.dev.model.Messages;
import com.dev.model.User;
import com.dev.service.EnviadosService;
import com.dev.service.MessagesService;
import com.dev.service.ProductService;
import com.dev.service.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;





@Controller
public class AppController {

	@Autowired
	private UserServices userService;
	
	@Autowired
	private MessagesService messageService;
	
	@Autowired
	private EnviadosService enviadosService;
	
	@Autowired
	private ProductService productService;
	
	
	

	@GetMapping(value={"", "index"})
	public String viewHomePage() {
		return "index";
	}
	

	
	@GetMapping(value={"/login"})
	public String viewLoginPage() {
		return "login";
	}
	
	@GetMapping(value={"/historia"})
	public String viewHistoryPage() {
		return "historia";
	}
	
	@GetMapping(value={"/help"})
	public String viewHelpPage() {
		return "ayuda";
	}
	
	@GetMapping(value={"/mapa"})
	public String viewMapa() {
		return "mapa";
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
	
	
	@RequestMapping("/users/{id}") 
	public ModelAndView showEditUserForm(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edituser");
		
		User user = userService.get(id);
		mav.addObject("user", user);
		
		return mav;
	}
	
	@RequestMapping(value = "/saveuser", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") User user) {
		userService.save(user);
		
		return "redirect:/users";
	}

	
	//LOGIN - USUARIO
	
	
	//MENSAJES
		@GetMapping("/messages")
		public String listMessages(Model model) {
			
			List<Messages> listMessages = messageService.listAll();
			model.addAttribute("listMessages", listMessages);

			return "messages";
		}
		
		@RequestMapping("/newMessages")
		public String showNewMessageForm(Model model) {
			Messages message = new Messages();
			model.addAttribute("message", message);
			
			return "new_message";
		}
		
		
		@RequestMapping(value = "/save1", method = RequestMethod.POST)
		public String saveMessage(@ModelAttribute("message") Messages message) {
			messageService.save(message);
			
			return "redirect:/enviados";
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
			
			return "redirect:/messages";
		}
		
		
		@RequestMapping("/responder/{id}")
		public ModelAndView showResponderForm(@PathVariable(name = "id") Integer id,Model model) {
			ModelAndView mav = new ModelAndView("responder");
			Messages message = messageService.get(id);
			mav.addObject("responder", message);
			
			Messages message1 = new Messages();
			model.addAttribute("message", message1);
			
			return mav;
		}
		

		//responder
		



		
		@GetMapping("/enviados")
		public String listEnviados(Model model) {
			
			List<Messages> listMessages = enviadosService.listAll();
			model.addAttribute("listMessages", listMessages);

			return "enviados";
		}


		@RequestMapping("/ver_enviado/{id}")
		public ModelAndView showEnviadoForm(@PathVariable(name = "id") Integer id) {
			ModelAndView mav = new ModelAndView("ver_enviado");
			
			Messages enviado = enviadosService.get(id);
			mav.addObject("enviado", enviado);
			
			return mav;
		}
		
		
		@RequestMapping("/borrarenviado/{id}")
		public String deleteEnviado(@PathVariable(name = "id") Integer id) {
			enviadosService.delete(id);
			
			return "redirect:/enviados";
		}
		
		
		// MENSAJES
		
		
		// PRODUCT
		
		// Add a product UI
	    @GetMapping("/add")
	    public String addProductUI(Model model) {
	        model.addAttribute("product", new Product());
	        return "add-product";
	    }
	    // Add a product API
	    @PostMapping("/add")
	    public String addProduct(@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile imageFile) throws IOException, IOException {
	        productService.addProduct(product, imageFile);
	        return "redirect:/get-products";
	    }
	    //Get all Products
	    @GetMapping("/get-products")
	    public String listProducts(Model model,@Param("keyword") String keyword,@Param("keyword1") String keyword1,@Param("keyword2") Float keyword2,@Param("keyword3") Float keyword3) {
	        List<Product> products = productService.getProducts(keyword,keyword1,keyword2,keyword3);
	        
	        model.addAttribute("products", products);
	        model.addAttribute("keyword", keyword);
			model.addAttribute("keyword1", keyword1);
			model.addAttribute("keyword2", keyword2);
			model.addAttribute("keyword3", keyword3);
	        
	        return "get-products";
	    }
	    
	    @GetMapping("/get-products-no")
	    public String listProductsNo(Model model,@Param("keyword") String keyword,@Param("keyword1") String keyword1,@Param("keyword2") Float keyword2,@Param("keyword3") Float keyword3) {
	        List<Product> products = productService.getProducts(keyword,keyword1,keyword2,keyword3);
	        
	        model.addAttribute("products", products);
	        model.addAttribute("keyword", keyword);
			model.addAttribute("keyword1", keyword1);
			model.addAttribute("keyword2", keyword2);
			model.addAttribute("keyword3", keyword3);
	        
	        return "get-products-no";
	    }
	    
	    //Get Image using product ID
	    @GetMapping(value = "/{productId}/image")
	    public ResponseEntity<byte[]> getProductImage(@PathVariable Long productId) {
	        Optional<Product> productOptional = productService.getProduct(productId);
	        if (productOptional.isPresent()) {
	            Product product = productOptional.get();
	            byte[] imageBytes = java.util.Base64.getDecoder().decode(product.getImage());
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.IMAGE_JPEG);
	            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
	        }
	    }
	    
	    @RequestMapping("/ver/{id}")
		public ModelAndView showverProductForm(@PathVariable(name = "id") Long id) {
			ModelAndView mav = new ModelAndView("ver_producto");
			
			Product product = productService.get(id);
			mav.addObject("product", product);
			
			return mav;
		}
	    
	    @RequestMapping("/edit/{id}")
		public ModelAndView showEditProductForm(@PathVariable(name = "id") Long id) {
			ModelAndView mav = new ModelAndView("edit_product");
			
			Product product = productService.get(id);
			mav.addObject("product", product);
			
			return mav;
		}	
	
		
		@RequestMapping("/delete/{id}")
		public String deleteProduct(@PathVariable(name = "id") Long id) {
			productService.delete(id);
			
			return "redirect:/get-products";
		}
	    
		
		// PRODUCT
	
	
}
