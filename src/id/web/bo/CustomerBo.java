package id.web.bo;

import java.util.List;

import id.web.service.Customer;

public interface CustomerBo {

	public void insert(Customer customer);
	public Customer findByCustomerId(int id);
	public List<Customer> findAllCustomers();
	public void update(Customer customer);
	public void deleteAll();
	public void delete(int id);
	
}
