package id.web.dao;

import java.util.List;

import id.web.service.Customer;

public interface CustomerDao {
	public void insert(Customer customer);
	public Customer findByCustomerId(int custId);
	public List<Customer> findAllCustomers();
	public void update(Customer customer);
	public void deleteAll();
	public void delete(int custId);
}
