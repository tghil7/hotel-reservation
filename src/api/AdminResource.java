package api;

import service.CustomerService;
import model.*;
import java.util.List;

public class AdminResource {
    private AdminResource(){}
    private static AdminResource adminResource = new AdminResource();
    public static AdminResource getInstance(){
        return adminResource;
    }

    public Customer getCustomer (String email){
        return CustomerService.getInstance().getCustomer(email);
    }

    public void addRooms(List<IRoom> rooms){

    }
}
