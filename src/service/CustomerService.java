package service;

import model.Customer;

import java.util.Collection;

public class CustomerService {
    public void addCustomer(String email, String firstName, String lastName){
        Customer customer = new Customer(email, firstName, lastName);
    }

    public Customer getCustomer (String customerEmail){
        return new Customer();
    }

    public Collection<Customer> getAllCustomers(){

    }
}
