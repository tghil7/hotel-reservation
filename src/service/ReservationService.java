package service;
import java.time.LocalDate;
import model.IRoom;
import model.Room;
import model.Reservation;
import java.util.*;
import model.Customer;


import java.util.HashMap;
import java.util.Map;

import static com.sun.jmx.mbeanserver.Util.cast;

public final class ReservationService {

    //Make constructor private
    private ReservationService(){ }
    //Static reference
    private static final  ReservationService reservationService = new ReservationService();
    //Collection to store all rooms
    private final  Map<String, Room> roomMap  = new HashMap<String, Room>();

    //Collection to store available rooms;
    private Collection <IRoom> availableRooms = new LinkedList<IRoom>();

    //Collection to store reservations.
    private final  Map<String, Collection<Reservation>> customerReservationMap = new HashMap<String, Collection<Reservation>>();

    //Collection to store list of reservations for individual customers.
    List<Reservation> customerReservationList = new LinkedList<>();
    boolean tryAgain = true; //Make sure the 7 in advance search is done once.



  public void addRoom(IRoom room){
     //Make the room free before adding it to the map, so it can be found when searching for free rooms.
      int n = 0;
      for (Room myRoom: roomMap.values()){
          if (myRoom.getRoomNumber().equals(room.getRoomNumber())){
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
      int n = 0;
      Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);



      Collection <Reservation> customerReservationList = getCustomersReservation(customer);//Get the individual list of reservations.
      if (customerReservationList ==null){
          customerReservationList = new LinkedList<>();
      }

      if (!(customerReservationMap.isEmpty())){
        for (Collection existingReservationCollection: customerReservationMap.values()) { //Loop through the collection reservation from the map
          //Cast the collection object into a linked list.
          LinkedList<Reservation> existingReserveCollection = new LinkedList<>();
          try{
              for (Object o : existingReservationCollection){
                  existingReserveCollection.add (cast(o));
              }
          }
          catch(ClassCastException ca){
              System.out.println("Unable to cast the collection into a linked list.");
            }
          for (Reservation existingReservation: existingReserveCollection){
                  //compare rooms

              if (existingReservation.getRoom().getRoomNumber().equals(room.getRoomNumber())) {
                  if (checkInDate.after(existingReservation.getCheckInDate()) && checkInDate.before(existingReservation.getCheckOutDate())){
                      System.out.println("This room is already booked for the chosen date.");
                      newReservation = null;
                      n++;
                      break;
                  }

                  else if (checkOutDate.after(existingReservation.getCheckInDate()) && checkOutDate.before(existingReservation.getCheckOutDate())) {
                      System.out.println("This room is already booked for the chosen time period+++");
                      n++;
                      break;
                  }

                  else if (checkInDate.equals(existingReservation.getCheckInDate())) {
                      System.out.println("This room is already booked for the chosen period!");
                      newReservation = null;
                      n++;
                      break;
                  }
              }


          }

       }

        if (n == 0){
            addNewReservation(customer,new Reservation(customer, room, checkInDate, checkOutDate), customerReservationList);
        }

      }

      else if (customerReservationMap.isEmpty()) {
          addNewReservation(customer,new Reservation(customer, room, checkInDate, checkOutDate), customerReservationList);

      }
      return newReservation;

  }

    private void addNewReservation(Customer customer, Reservation newReservation, Collection<Reservation> customerReservationList) {
        customerReservationList.add(newReservation);
        customerReservationMap.put(customer.getEmail(), customerReservationList);
        //Remove the room that was just reserved from the list of available rooms.
        //availableRooms.remove(room);

        //Reserve: existing reservation changed to existingReservation.
    }


    public Collection<IRoom> findRooms (Date checkInDate, Date checkOutDate) {
      //First get free rooms
        Collection potentialFreeRooms = getFreeRooms(checkInDate, checkOutDate);
        if (customerReservationMap.isEmpty()){
            for (Room thisRoom: roomMap.values()){
                potentialFreeRooms.add(thisRoom);
            }
        }
    /*    else if (!customerReservationMap.isEmpty()) {
            if (potentialFreeRooms.isEmpty() && tryAgain) {
                    //Convert my dates to local dates and add 7 days.
                    getReservationWithNewDates(checkInDate, checkOutDate);
                    tryAgain = false;

            }
        } */

      return potentialFreeRooms;
  }

    public Date getReservationWithNewDate(Date checkInDate) {
       //System.out.println("No room found. Searching during the same period of time after 7 days... ");
        LocalDate checkIn = new java.sql.Date(checkInDate.getTime()).toLocalDate();
        checkIn = checkIn.plusDays(7);
        //Convert them back to date before calling the function again.
        Date newCheckIn = java.sql.Date.valueOf(checkIn);
        //findRooms(newCheckIn, newCheckOut);
        checkInDate = newCheckIn;
       return checkInDate;
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

  //Get all the reserved rooms for the dates entered as input
    public Collection getAllReservations (Date checkInDate, Date checkOutDate){
      Collection reservedRooms = new LinkedList<Room>();
        for (Collection reservationCollection : customerReservationMap.values()) {
            LinkedList<Reservation> existingReserveCollection = new LinkedList<Reservation>(reservationCollection);
            if (!existingReserveCollection.isEmpty()) {
                for (Reservation reservation : existingReserveCollection) {
                    if (checkInDate.after(reservation.getCheckInDate()) && checkInDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckOutDate())) {
                        reservedRooms.add(reservation.getRoom());
                    } else if (checkInDate.before(reservation.getCheckInDate()) && checkOutDate.after(reservation.getCheckInDate()) && checkOutDate.after(reservation.getCheckOutDate())) {
                        reservedRooms.add(reservation.getRoom());
                    } else if (checkInDate.equals(reservation.getCheckInDate()) && checkOutDate.equals(reservation.getCheckInDate())) {
                        reservedRooms.add(reservation.getRoom());
                    }
                    else if (checkInDate.equals(reservation.getCheckInDate()) && checkOutDate.before(reservation.getCheckInDate())){
                        reservedRooms.add(reservation.getRoom());
                    }
                    else if (checkInDate.equals(reservation.getCheckInDate()) && checkOutDate.after(reservation.getCheckInDate())){
                        reservedRooms.add(reservation.getRoom());
                    }
                    else if (checkInDate.after(reservation.getCheckInDate()) && checkOutDate.before(reservation.getCheckInDate())){
                        reservedRooms.add(reservation.getRoom());
                    }


                }

            }
            else if (existingReserveCollection.isEmpty()){
                System.out.println("The list of reservations is empty.");
            }
        }
      return reservedRooms;
    }

    //Get all free rooms
    public Collection getFreeRooms(Date checkInDate, Date checkOutDate){
      int count = 0;
        Collection freeRooms = new LinkedList<Room>();
        Collection myReservedRooms = getAllReservations(checkInDate, checkOutDate);
        //System.out.println("Reserved rooms: " + myReservedRooms.toString());
      for (Room room : roomMap.values()){
         if (!myReservedRooms.contains(room)){
             for (Object bookedRoom : myReservedRooms){
                 if (((Room)bookedRoom).getRoomNumber() == room.getRoomNumber()){
                    // freeRooms.remove(room);
                     System.out.println("No need to add the room again for this period");
                 }
                 else{
                     freeRooms.add(room);
                 }
             }


         }
      }

        return freeRooms;
    }

  //Return all rooms
    public Collection getAllRooms(){
      return roomMap.values();
    }
    public static void main (String [] args){

    }
}
