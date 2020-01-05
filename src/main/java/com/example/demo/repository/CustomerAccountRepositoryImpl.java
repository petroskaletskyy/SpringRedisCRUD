package com.example.demo.repository;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CustomerAccount;

@Repository
public class CustomerAccountRepositoryImpl implements CustomerAccountRepository {
	
	private Logger logger = LoggerFactory.getLogger(CustomerAccountRepositoryImpl.class);

	private static final String KEY = "CustomerAccount";
	
	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, Long, CustomerAccount> hashOperations;
	
	@Autowired
	public CustomerAccountRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void save(CustomerAccount customerAccount) {
		logger.debug("Create new customerAccount {} :" + customerAccount);
		hashOperations.put(KEY, customerAccount.getId(), customerAccount);
	}

	@Override
	public CustomerAccount findById(long id) {
		logger.debug("Find customerAccount by id: " + id);
		return hashOperations.get(KEY, id);
	}

	@Override
	public Map<Long, CustomerAccount> findAll() {
		logger.debug("Find all customer accounts");
		return hashOperations.entries(KEY);
	}

	@Override
	public void update(CustomerAccount customerAccount) {
		logger.debug("Update customerAccount {}: " + customerAccount);
		hashOperations.put(KEY, customerAccount.getId(), customerAccount);
	}

	@Override
	public void delete(long id) {
		logger.debug("Delete customerAccount by id{}:" + id);
		hashOperations.delete(KEY, id);
	}
	
	
}
