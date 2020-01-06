package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.CustomerAccount;
import com.example.demo.repository.CustomerAccountRepositoryImpl;

@RestController
public class CustomerAccountController {

	@Autowired
	private CustomerAccountRepositoryImpl customerAccountRepositoryImpl;

	@PostMapping(value = "/add", consumes = { "application/json" }, produces = { "application/json" })
	@ResponseBody
	public ResponseEntity<CustomerAccount> save(@RequestBody CustomerAccount customerAccount,
			UriComponentsBuilder builder) {
		customerAccountRepositoryImpl.save(customerAccount);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/add/{id}").buildAndExpand(customerAccount.getId()).toUri());
		return new ResponseEntity<CustomerAccount>(headers, HttpStatus.CREATED);
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseEntity<Map<Long, CustomerAccount>> getAll() {
		Map<Long, CustomerAccount> customerAccounts = customerAccountRepositoryImpl.findAll();
		return new ResponseEntity<Map<Long, CustomerAccount>>(customerAccounts, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	@ResponseBody
	public ResponseEntity<CustomerAccount> getById(@PathVariable Long id) {
		CustomerAccount customerAccount = customerAccountRepositoryImpl.findById(id);
		return new ResponseEntity<CustomerAccount>(customerAccount, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	@ResponseBody
	public ResponseEntity<CustomerAccount> update(@RequestBody CustomerAccount customerAccount) {
		if (customerAccount != null) {
			customerAccountRepositoryImpl.update(customerAccount);
		}
		return new ResponseEntity<CustomerAccount>(customerAccount, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<CustomerAccount> deleteById(@PathVariable Long id) {
		customerAccountRepositoryImpl.delete(id);
		return new ResponseEntity<CustomerAccount>(HttpStatus.ACCEPTED);
	}
}
