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

    //Make constructor private
    private ReservationService(){ }
    //Static reference
    private static final  ReservationService reservationService = new ReservationService();
    //Collection to store all rooms
    private Map<String, Room> roomMap  = new HashMap<String, Room>();

    //Collection to store available rooms;
    private Collection <IRoom> availableRooms = new LinkedList<IRoom>();

    //Collection to store reservations.
    private Map<String, Collection<Reservation>> customerReservationMap = new HashMap<String, Collection<Reservation>>();

    //Collection to store list of reservations for individual customers.
    List<Reservation> customerReservationList = new LinkedList<>();
    boolean tryAgain = true; //Make sure the 7 in advance search is done once.



  public void addRoom(IRoom room){
     //Make the room free before adding it to the map so it can be found when searching for free rooms.
      int n = 0;
      for (Room myRoom: roomMap.values()){
          if (myRoom.getRoomNumber() == room.getRoomNumber()){
              n++;
          }
      }
      if (n == 0) {
          roomMap.put(room.getRoomNumber(), (Room) room);
      }
  }

  public static ReservationService getInstance(){
      return reservationService;
  }

  public  IRoom getARoom(String roomId){
      return roomMap.get(roomId);
  }

  public Reservation reserveARoom (Customer customer, IRoom room, Date checkInDate, Date checkOutDate ){
      //Add the new reservation to the set of reservations.
      Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);

      Collection <Reservation> customerReservationList = getCustomersReservation(customer);//Get the individual list of reservations.
      if (customerReservationList ==null){
          customerReservationList = new LinkedList<>();
      }
      if (customerReservationMap.isEmpty()) {
          customerReservationList.add(newReservation);
          customerReservationMap.put(customer.getEmail(), customerReservationList);
      }
      else if (!customerReservationMap.isEmpty()){
        for (Collection existingReservationCollection: customerReservationMap.values()) { //Loop through the collection reservation from the map
          //Cast the collection object into a linked list.
          LinkedList<Reservation> existingReserveCollection = (LinkedList<Reservation>) existingReservationCollection;
          for (Reservation existingReservation: existingReserveCollection){
              if (existingReservation.equals(newReservation)) {
               availableRooms = findRooms(checkInDate, checkOutDate);
              }


                  //compare rooms
              else if ((existingReservation.getRoom().getRoomNumber() == room.getRoomNumber()) && checkInDate.before(existingReservation.getCheckInDate()) && checkOutDate.before(existingReservation.getCheckInDate())) {
                      customerReservationList.add(newReservation);
                      customerReservationMap.put(customer.getEmail(), customerReservationList);
                      //Remove the room that was just reserved from the list of available rooms.
                      //availableRooms.remove(room);

                      //Reserve: existing reservation changed to existingReservation.
                  }
              else if ((existingReservation.getRoom().getRoomNumber() == room.getRoomNumber()) &&checkInDate.after(existingReservation.getCheckInDate()) && checkInDate.before(existingReservation.getCheckOutDate())) {
                      System.out.println("This room is already booked for the chosen date");
                      newReservation = null;
                      break;
              }
              else if ((existingReservation.getRoom().getRoomNumber() == room.getRoomNumber()) && checkOutDate.after(existingReservation.getCheckInDate()) && checkOutDate.before(existingReservation.getCheckOutDate())) {
                      System.out.println("This room is already booked for the chosen time period");
                      newReservation = null;
                      break;
              }
              else if (checkInDate.after(existingReservation.getCheckInDate())) {
                      customerReservationList.add(newReservation);
                      customerReservationMap.put(customer.getEmail(), customerReservationList);
                      //Remove the room that was just reserved from the list of available rooms.
                      //availableRooms.remove(room);
              }
              else if ((existingReservation.getRoom().getRoomNumber() == room.getRoomNumber()) && checkInDate.compareTo(existingReservation.getCheckInDate()) == 0) {
                      System.out.println("This room is already booked for the chosen period.");
                      newReservation = null;
                      break;
              }
              else {
                      customerReservationList.add(newReservation);
                      customerReservationMap.put(customer.getEmail(), customerReservationList);
              }
          }

       }
      }
      return newReservation;

  }








  public Collection<IRoom> findRooms (Date checkInDate, Date checkOutDate) {
      //Look in the set of reservation, if one of them is available with check in  and check out dates before reservation check
      // , add it to the list of
      //available rooms, and return that list of available rooms.
      if (customerReservationMap.isEmpty()){//If there are no reservations, return the full list of rooms
          for (Room myRoom: roomMap.values()) {
              if(!(availableRooms.contains(myRoom))) {
                  availableRooms.add(myRoom);
              }
          }
      }
      else if (!customerReservationMap.isEmpty()){//It seems that my code is not going past this step.
          for (Collection reservationCollection : customerReservationMap.values()) {
              LinkedList<Reservation> existingReserveCollection = (LinkedList<Reservation>)reservationCollection;
              for (Reservation reservation : existingReserveCollection) {
                  if (checkInDate.before(reservation.getCheckInDate()) && !(availableRooms.contains(reservation.getRoom()))) {
                      if (checkOutDate.before(reservation.getCheckOutDate())) {
                          availableRooms.add(reservation.getRoom());
                      }
                  } else if (checkInDate.after(reservation.getCheckOutDate())) {
                      availableRooms.add(reservation.getRoom());
                  } else if (checkInDate.after(reservation.getCheckInDate()) && checkInDate.before(reservation.getCheckOutDate())) {
                      System.out.println("Room" +  reservation.getRoom() +  " booked for this period");
                      continue;
                  }

                  //Also add each room from the big room Map that is not in the reservation list.

              }
          }
      }
      //If available rooms list is still empty, add 7 days to the search
      else if (availableRooms.isEmpty()) {

          do {
              //Convert my dates to local dates and add 7 days.
              System.out.println("No room found. Searching during the same period of time after 7 days... ");
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
      return customerReservationMap.get(customer.getEmail());

  }

  public void printAllReservation(){
      for (Collection reservation  : customerReservationMap.values()){
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
