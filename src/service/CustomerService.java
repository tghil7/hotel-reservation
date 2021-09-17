package service;

import model.Customer;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;


public  final class CustomerService {

    private Map <String,Customer> customerQueue  = new HashMap<String,Customer>();

    public void addCustomer(String email, String firstName, String lastName){
        Customer customer = new Customer(email, firstName, lastName);
        customerQueue.put(email,customer);
    }

    public Customer getCustomer (String customerEmail){
      return  customerQueue.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers(){
        return customerQueue.values();
    }
}
