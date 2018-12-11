package id.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import id.web.dao.CustomerDao;
import id.web.service.Customer;

public class CustomerDaoImpl implements CustomerDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public void insert(Customer customer) {
		this.sessionFactory.getCurrentSession().save(customer);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Customer findByCustomerId(int custId) {
		List<Customer> list = this.sessionFactory
				.getCurrentSession()
				.createQuery("from Customer where custId=:custId")
				.setParameter("custId", custId).list();
		if (list.isEmpty()) {
			return null;
		} else {
			return (Customer)list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Customer> findAllCustomers() {
		List<Customer> custList = new ArrayList<Customer>();
		custList = this.sessionFactory.getCurrentSession()
				.createQuery("from Customer").list();
		return custList;
	}

	@Transactional
	public void update(Customer customer) {
		this.sessionFactory.getCurrentSession().update(customer);
	}

	@Transactional
	public void deleteAll() {
		Query query = this.sessionFactory.getCurrentSession().createQuery("DELETE From Customer");
		int result = query.executeUpdate();
	}

	@Transactional
	public void delete(int custId) {
		Customer customer = findByCustomerId(custId);
		if (customer != null) {
			this.sessionFactory.getCurrentSession().delete(customer);
		}
	}

}
