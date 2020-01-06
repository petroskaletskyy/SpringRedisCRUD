package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.model.CustomerAccount;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRedisCrudApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class SpringRedisCrudApplicationTests {

	@LocalServerPort
	private int port;

	private String getUrl() {
		return "http://localhost:" + port;
	}

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetAllCustomerAccount() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<CustomerAccount> entity = new HttpEntity<CustomerAccount>(null, headers);
		ResponseEntity<CustomerAccount> responseEntity = restTemplate.exchange(getUrl() + "/findAll", HttpMethod.GET,
				entity, CustomerAccount.class);
		assertNotNull(responseEntity.getBody());
	}

	@Test
	public void testAddCustomerAccount() {
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setFirstName("FirstName");
		customerAccount.setLastName("LastName");
		customerAccount.setEmail("test@mail.com");
		customerAccount.setBalance(1589.00);
		customerAccount.setId(15);
		ResponseEntity<CustomerAccount> postResponse = restTemplate.postForEntity(getUrl() + "/add", customerAccount,
				CustomerAccount.class);
		assertNotNull(postResponse);
	}

	@Test
	public void testGetCustomerAccountById() {
		CustomerAccount customerAccount = restTemplate.getForObject(getUrl() + "/find/15", CustomerAccount.class);
		System.out.println(customerAccount.getFirstName() + " " + customerAccount.getLastName());
		assertNotNull(customerAccount);
	}
	
	@Test
	public void testUpdateCustomerAccount() {
		int id = 15;
		CustomerAccount customerAccount = restTemplate.getForObject(getUrl() + "/find/" + id, CustomerAccount.class);
		customerAccount.setBalance(1000000.00);
		restTemplate.put(getUrl() + "/update", customerAccount);
		CustomerAccount updatedCustomerAccount = restTemplate.getForObject(getUrl() + "/update", CustomerAccount.class);
		System.out.println(customerAccount.toString());
		assertNotNull(updatedCustomerAccount);
	}
	
	@Test
	public void testDeleteCustomerAccount() {
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setFirstName("Test");
		customerAccount.setLastName("Test");
		customerAccount.setEmail("test@mail.com");
		customerAccount.setBalance(1589.00);
		customerAccount.setId(20);
		int id = (int)customerAccount.getId();
		ResponseEntity<CustomerAccount> postResponse = restTemplate.postForEntity(getUrl() + "/add", customerAccount,
				CustomerAccount.class);
		assertNotNull(postResponse);
		CustomerAccount findCustomerAccount = restTemplate.getForObject(getUrl() + "/find/" + id, CustomerAccount.class);
		assertNotNull(findCustomerAccount);
		restTemplate.delete(getUrl() + "/delete/" + id);
		try {
			
			customerAccount = restTemplate.getForObject(getUrl() + "/find/" + id, CustomerAccount.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	} 

}
