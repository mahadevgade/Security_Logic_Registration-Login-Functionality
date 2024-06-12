package com.avecircle.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.avecircle.entity.Customer;
import com.avecircle.repo.CustomerRepo;

@RestController
public class CustomerRestcontroller {
	
	@Autowired
	private CustomerRepo repo;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping("/register")
	public ResponseEntity<String> registrationCustomer(@RequestBody Customer customer)
	{
		// to check unique users
		
		customer.setPassword(pwdEncoder.encode(customer.getPassword()));
		
		repo.save(customer);
		
		return new ResponseEntity<String>("Cutomer Register Succesfully...", HttpStatus.OK);
	}

	
	@PostMapping("/login")
	public ResponseEntity<String> loginCheck(@RequestBody Customer customer)
	{
		UsernamePasswordAuthenticationToken token=
				new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword());
		
		try {
			Authentication authenticate = authManager.authenticate(token);

			if (authenticate.isAuthenticated()) {
				
				return new ResponseEntity<String>("Welcome to Admin Dashbord...", HttpStatus.OK);
				
			}
		} catch (Exception e) {
			// logger need to write here
			
		}
		return new ResponseEntity<String>("Invalid Credentials...", HttpStatus.BAD_REQUEST);
			
	}
}






















