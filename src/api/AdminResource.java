package api;

import service.CustomerService;
import model.*;
import service.ReservationService;
import java.util.*;

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

    //Add additional rooms
    public void addRooms(List<IRoom> rooms){
        for ( IRoom room : rooms){
            ReservationService.getInstance().addRoom(room);
        }

    }

    public Collection <IRoom> getAllRooms(){
      return ReservationService.getInstance().getAllRooms();
    }

    public Collection <Customer> getAllCustomers(){
        return CustomerService.getInstance().getAllCustomers();
    }

    public void displayAllReservations(){
        ReservationService.getInstance().printAllReservation();
    }
}
