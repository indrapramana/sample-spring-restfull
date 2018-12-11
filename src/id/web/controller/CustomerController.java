package id.web.controller;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import id.web.bo.CustomerBo;
import id.web.service.Customer;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerBo customerBo;
	
	private static final Logger logger = Logger.getLogger(CustomerController.class);
	
	@RequestMapping(value="/customers", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Customer>> listAllCustomers() {
		logger.info("Rerieve all customers");
		
		List<Customer> customers = customerBo.findAllCustomers();
		
		if (customers.isEmpty()) {
			// or HttpStatus.NOT_FOUND
			return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Customer>>(customers, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/customers/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") int id) {
		logger.info("Fetching Customer with id " + id);
		Customer customer = customerBo.findByCustomerId(id);
		
		if (customer == null) {
			logger.info("Customer with id " + id + " not found");
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Customer>(customer, new HttpHeaders(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/customer", method=RequestMethod.POST)
	public ResponseEntity<Void> createCustomer(@RequestBody Customer cust, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Customer " + cust.getFullname());
		Customer customer = new Customer(cust.getCustId(), cust.getFullname(), cust.getAddress(), cust.getEmail());
		
		try {
			customerBo.insert(customer);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/list-customer/{id}").buildAndExpand(cust.getCustId()).toUri());
			
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/customer", method=RequestMethod.PUT)
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer cust) {
		logger.info("Updating Customer " + cust.getCustId());
		Customer customer = customerBo.findByCustomerId(cust.getCustId());
		
		if (customer == null) {
			logger.info("Customer with id " + cust.getCustId() + " not found");
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
		
		customerBo.update(cust);
		return new ResponseEntity<Customer>(cust, HttpStatus.OK);
	}
	
	@RequestMapping(value="/customer/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") int id) {
		logger.info("Fetching & Deleting Customer with id " + id);
		Customer customer = customerBo.findByCustomerId(id);
		
		if (customer == null) {
			logger.info("Unable to delete. Customer with id " + id + " not found");
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
		
		customerBo.delete(id);
		return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
	}
}
