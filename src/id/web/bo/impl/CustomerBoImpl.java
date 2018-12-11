package id.web.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import id.web.bo.CustomerBo;
import id.web.dao.CustomerDao;
import id.web.service.Customer;

public class CustomerBoImpl implements CustomerBo {
	
	@Autowired
	private CustomerDao custDao;

	public CustomerDao getCustDao() {
		return custDao;
	}

	public void setCustDao(CustomerDao custDao) {
		this.custDao = custDao;
	}

	public void insert(Customer customer) {
		custDao.insert(customer);
	}

	public Customer findByCustomerId(int id) {
		return custDao.findByCustomerId(id);
	}

	public List<Customer> findAllCustomers() {
		return custDao.findAllCustomers();
	}

	public void update(Customer customer) {
		custDao.update(customer);
	}

	public void deleteAll() {
		custDao.deleteAll();
	}

	public void delete(int id) {
		custDao.delete(id);
	}

}
