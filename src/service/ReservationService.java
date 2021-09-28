package service;

import model.IRoom;
import model.Room;
import model.Reservation;
import java.util.*;
import model.Customer;


import java.util.HashMap;
import java.util.Map;

public final class ReservationService {

    private ReservationService(){ }
    //Static reference
    private static ReservationService reservationService = new ReservationService();
    //Collection to store all rooms
    private Map<String, Room> roomMap  = new HashMap<String, Room>();

    //Collection to store available rooms;
    private List<IRoom> availableRooms = new LinkedList<IRoom>();

    //Collection to store reservations.
    private Set<Reservation> customerReservation = new HashSet<Reservation>();

    //Collection to store list of reservations for individual customers.
    List<Reservation> customerReservationList = new ArrayList<>();



  public void addRoom(IRoom room){
     //Make the room free before adding it to the map so it can be found when searching for free rooms.
      roomMap.put(room.getRoomNumber(), (Room) room);
  }

  public static ReservationService getInstance(){
      return reservationService;
  }

  public IRoom getARoom(String roomId){
      return roomMap.get(roomId);
  }

  public Reservation reserveARoom (Customer customer, IRoom room, Date checkInDate, Date checkOutDate ){
      //Add the new reservation to the set of reservations.
      Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
      customerReservation.add(reservation);
      return reservation;

  }

  public Collection<IRoom> findRooms (Date checkInDate, Date checkOutDate) {
      //Look in the set of reservation, if one of them is available with check in  and check out dates before reservation check
      // , add it to the list of
      //available rooms, and return that list of available rooms.
      if (customerReservation.isEmpty()){//If there are no reservations, return the full list of rooms
          for (Room myRoom: roomMap.values()) {
              availableRooms.add(myRoom);
          }
      }
      for (Reservation reservation : customerReservation) {
          IRoom room = reservation.getRoom();
          if (checkInDate.before(reservation.getCheckInDate()) && !(availableRooms.contains(room))) {
              if (checkOutDate.before(reservation.getCheckOutDate())) {
                  availableRooms.add(room);
              }
          }
          else if (checkInDate.after(checkOutDate)){
              availableRooms.add(room);
          }

          //Also add each room from the big room Map that is not in the reservation list.
          for (IRoom mapRoom: roomMap.values()){
              if (!room.equals(mapRoom)) {
                  availableRooms.add(mapRoom);
              }
          }
      }

     //Return the list of available rooms.
      return availableRooms;
  }

  //Get the reservation for an individual customer.
  public Collection<Reservation> getCustomersReservation(Customer customer){
      //Loop through all the reservations in the list. For each of them, if the customer matches the customer passed as argument, return it.

      for (Reservation reservation: customerReservation){
          if (reservation.getCustomer(customer.getEmail()).equals(customer)){
              customerReservationList.add(reservation);
          }
      }
      return customerReservationList;
  }

  public void printAllReservation(){
      for (Reservation reservation : customerReservation){
         System.out.println(reservation.toString());
      }
  }

  //Return all rooms
    public Collection getAllRooms(){
      return roomMap.values();
    }
    public static void main (String [] args){

    }
}
