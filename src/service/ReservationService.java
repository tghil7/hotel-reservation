package service;
import java.time.LocalDate;
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
      for (Reservation reserve: customerReservation){
          if (reserve.getRoom().equals(reservation.getRoom())){
              if(checkInDate.before(reservation.getCheckInDate()) && checkOutDate.before(reservation.getCheckInDate())){
                  customerReservation.add(reservation);
                  //Remove the room that was just reserved from the list of available rooms.
                  availableRooms.remove(room);
              }
              else if (checkInDate.after(reservation.getCheckInDate()) && checkInDate.before(reservation.getCheckOutDate())){
                 System.out.print("This room is already booked for the chosen");
                 break;
              }
              else if (checkOutDate.after(reservation.getCheckInDate()) && checkOutDate.before(reservation.getCheckOutDate())){
                  System.out.print("This room is already booked for the chosen");
                  break;
              }
              else if(checkInDate.after(reservation.getCheckInDate())){
                  customerReservation.add(reservation);
                  //Remove the room that was just reserved from the list of available rooms.
                  availableRooms.remove(room);
              }
          }
      }

      return reservation;

  }

  public Collection<IRoom> findRooms (Date checkInDate, Date checkOutDate) {
      //Look in the set of reservation, if one of them is available with check in  and check out dates before reservation check
      // , add it to the list of
      //available rooms, and return that list of available rooms.
      if (customerReservation.isEmpty()){//If there are no reservations, return the full list of rooms
          for (Room myRoom: roomMap.values()) {
              if(!(availableRooms.contains(myRoom))) {
                  availableRooms.add(myRoom);
              }
          }
      }
      else {
          for (Reservation reservation : customerReservation) {
              IRoom room = reservation.getRoom();
              if (checkInDate.before(reservation.getCheckInDate()) && !(availableRooms.contains(room))) {
                  if (checkOutDate.before(reservation.getCheckOutDate())) {
                      availableRooms.add(room);
                  }
              }
              else if (checkInDate.after(reservation.getCheckOutDate())) {
                  availableRooms.add(room);
              }

              else if (checkInDate.after(reservation.getCheckInDate()) && checkInDate.before(reservation.getCheckOutDate())){
                  System.out.println("Room booked for this period");
              }

                  //Also add each room from the big room Map that is not in the reservation list.
                  for (IRoom mapRoom : roomMap.values()) {
                      if (!room.equals(mapRoom)&& !availableRooms.contains(mapRoom)) {
                          availableRooms.add(mapRoom);
                      }
                  }

          }
      }
      //If available rooms list is still empty, add 7 days to the search
      if (availableRooms.isEmpty()){
          boolean tryAgain = true;
          do {
              //Convert my dates to local dates and add 7 days.
              LocalDate checkIn = new java.sql.Date(checkInDate.getTime()).toLocalDate();
              checkIn = checkIn.plusDays(7);
              LocalDate checkOut = new java.sql.Date(checkInDate.getTime()).toLocalDate();
              checkOut = checkOut.plusDays(7);
              //Convert them back to dates before calling the function again.
              Date newCheckIn = java.sql.Date.valueOf(checkIn);
              Date newCheckOut = java.sql.Date.valueOf(checkOut);
              findRooms(newCheckIn, newCheckOut);
              tryAgain = false;
          }while (tryAgain);
      }
     //Return the list of available rooms.
      return availableRooms;
  }

  //Get the reservation for an individual customer.
  public Collection<Reservation> getCustomersReservation(Customer customer){
      //Loop through all the reservations in the list. For each of them, if the customer matches the customer passed as argument, return it.

      for (Reservation reservation: customerReservation){
          if (reservation.getCustomer(customer.getEmail()).equals(customer) && !(customerReservationList.contains(reservation))){
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
