package com.example.demo.repository;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.CustomerAccount;

@Service
public interface CustomerAccountRepository {

	void save(CustomerAccount customerAccount);

	CustomerAccount findById(long id);

	Map<Long, CustomerAccount> findAll();

	void update(CustomerAccount customerAccount);

	void delete(long id);

}
