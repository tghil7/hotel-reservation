package service;

import model.Customer;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;


public class CustomerService {
    //Static reference

    private static CustomerService  customerService = new CustomerService();
    public static CustomerService getInstance(){
        return customerService;
    }

    private Map <String,Customer> customerMap  = new HashMap<String,Customer>();


    public void addCustomer(String firstName, String lastName, String email){
        customerMap.put(email, new Customer(firstName, lastName, email));
    }

    public Customer getCustomer (String customerEmail){
      return  customerMap.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers(){
        return customerMap.values();
    }
}
