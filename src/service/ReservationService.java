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
    private Map<String, Room> roomQueue  = new HashMap<String, Room>();

    //Collection to store available rooms;
    private Set<IRoom> availableRooms = new HashSet<IRoom>();

    //Collection to store reservations.
    private Set<Reservation> customerReservation = new HashSet<Reservation>();
  public void addRoom(IRoom room){
      roomQueue.put(room.getRoomNumber(), (Room) room);

  }

  public static ReservationService getInstance(){
      return reservationService;
  }

  public IRoom getARoom(String roomId){
      return roomQueue.get(roomId);
  }

  public Reservation reserveARoom (Customer customer, IRoom room, Date checkInDate, Date checkOutDate ){
      //Add the new reservation to the set of reservations.
      Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
      customerReservation.add(reservation);
      return reservation;

  }

  public Collection<IRoom> findRooms (Date checkInDate, Date checkOutDate) {
      for (IRoom room : roomQueue.values()) {
          if (room.isFree()) {
              availableRooms.add(room);
          }
      }
      return availableRooms;
  }

  public Collection<Reservation> getCustomersReservation(Customer customer){
       return customerReservation;
  }

  public void PrintAllReservation(){
      for (Reservation reservation : customerReservation){
          reservation.toString();
      }
  }
}
