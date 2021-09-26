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

  public void addRoom(IRoom room){
      room.setRoomStatus(true); //Make the room free before adding it to the map so it can be found when searching for free rooms.
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
      //Look in the map of rooms, if one of them is available, add it to the list of
      //available rooms, and return that list of available rooms.
      for (Room room : roomMap.values()) {
          if (room.isFree()) {
              availableRooms.add(room);
          }
      }
      return availableRooms;
  }

  //Get the reservation for an individual customer.
  public Collection<Reservation> getCustomersReservation(Customer customer){
       return customerReservation;
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
