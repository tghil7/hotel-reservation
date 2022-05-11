package api;

import model.Room;
import model.Reservation;
import java.time.LocalDate;
import java.util.Scanner;
 import java.util.Date;
 import java.util.*;
 import java.text.*;
import java.util.regex.Pattern;

public class MainMenu {

    //Crate scanner to read user input.
    Scanner kb = new Scanner(System.in);
    Date checkIn ;
    Date checkOut ;
    Date sevenDaysCheckIn; // Date fields to hold the new dates after adding 7 days.
    Date sevenDaysCheckOut;

    boolean keepRun = true;
    int action= 0;

    //Date input formatter
    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
    //Get the user input
    public int getAction(){
        System.out.println ("========================================================");
        System.out.println("Main menu:" +
                "\n" + "1. Find and reserve a room" + "\n" +
                "2. See my reservations" + "\n" +
                "3. Create an account" + "\n" +
                "4. Admin" + "\n" +
                "5. Exit" + "\n");
      System.out.println ("========================================================");
      System.out.println("Please enter a number for the menu item to be accessed:");
      int response = 0;
      if (kb.hasNextInt()) {
          response = kb.nextInt();
      }
      else {
          kb.nextLine();
      }
          return response;

    }
    public void startActions() {
        while (keepRun) {
            action = getAction();//collect input


            switch (action) {
                case 1:
                    //Find and reserve a room
                    findAndReserveRoom();
                    break;
                case 2:
                    retrieveCustomerReservation();
                    break;
                //...other case statements
                case 3:
                    //3. Create an account
                   createAccount();
                    break;

                case 4:
                    //Go to admin menu
                    callAdminMenu();
                    break;
                case 5:
                    //Exit the application
                    exitApplication();
                    break;


            }
        }
    }

    private void exitApplication() {
        kb.nextLine();
        System.out.println("Exiting Main menu.....");
        keepRun = false;
        System.exit(0);
    }

    private void callAdminMenu() {
        kb.nextLine();
        //switch to admin menu here
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.start();
    }

    private void createAccount() {
        kb.nextLine();
        System.out.println("Please enter your first name");
        String firstName = kb.nextLine();
        System.out.println("Please enter your last name");
        String lastName = kb.nextLine();
        System.out.println("Please enter your email address:");
        String emailAddress = "";
        try {
            emailAddress = kb.nextLine();
        }
        catch(IllegalArgumentException e){
          System.out.println("Please enter a valid email address");
        }
        HotelResource.getInstance().CreateACustomer(firstName, lastName, emailAddress);
        System.out.println("Account for customer " + firstName + " " + lastName + " successfully created.");
    }

    private void retrieveCustomerReservation() {
        kb.nextLine();
        //2. See my reservations
        //Check that customer has an account first before trying to access their reservation.
        System.out.println("Please enter your email address:");
        String customerEmail = kb.nextLine();

        //call the hotel resource customer reservation method.
        try{//Check if there is no reservation.
            System.out.println(HotelResource.getInstance().getCustomersReservation(customerEmail).toString());

        }
        catch (NullPointerException e){
            System.out.println("No reservation was made using this email address:" + customerEmail);
            //Get the customer's reservation using their email.
           // System.out.println(HotelResource.getInstance().getCustomersReservation(customerEmail));
           // System.out.println(HotelResource.getInstance().getCustomersReservation(customerEmail).toString());
        }


    }

    private void findAndReserveRoom() {
        //Find and reserve a room.
        kb.nextLine();
        System.out.println("Please enter your email address:");
        String email = kb.nextLine();
        //If the customer did not register yet. Ask him to register.
        try {
            HotelResource.getInstance().getCustomer(email).toString();
        }
        catch (NullPointerException e){
            System.out.println("Customer did not register yet. Please use menu 3 to create an account first.");
            return;
        }


        System.out.println("Please enter check in date(format MMM-dd-yyyy):");
        String checkInDate = kb.nextLine();
        System.out.println("Please enter check out date (format MMM-dd-yyyy):");
        String checkOutDate = kb.nextLine();


        try {
            checkIn = formatter.parse(checkInDate);
            checkOut = formatter.parse(checkOutDate);



        } catch (ParseException e) {
            System.out.println("Please enter valid dates in the format MMM-dd-yyyy.");

        }

        if (HotelResource.getInstance().findARoom(checkIn, checkOut).isEmpty()) {
            System.out.println("No room available for the dates selected.Searching for rooms available 7 days later...");
            sevenDaysCheckIn = HotelResource.getInstance().getNewDate(checkIn);
            System.out.println("Tentative new check in date:" + sevenDaysCheckIn);
            sevenDaysCheckOut = HotelResource.getInstance().getNewDate(checkOut);
            System.out.println("Tentative new check out date:" + sevenDaysCheckOut);
            if (HotelResource.getInstance().findARoom(sevenDaysCheckIn, sevenDaysCheckOut).isEmpty()){
                System.out.println ("Unfortunately, there are  still no rooms available from  " + sevenDaysCheckIn + " to " + sevenDaysCheckOut);
            }
            else {
                bookTheRoom(email, "7 days from " + checkInDate);
            }
        }

        else {
            bookTheRoom(email, checkInDate);


        }

    }

    private void bookTheRoom(String email, String checkInDate) {
        //String debug = HotelResource.getInstance().findARoom(checkIn, checkOut).toString();
        //Get the reserved dates
        String myReservedDates;

        if (HotelResource.getInstance().findARoom(checkIn, checkOut).toString().equalsIgnoreCase("[]")){
            System.out.println(HotelResource.getInstance().findARoom(sevenDaysCheckIn, sevenDaysCheckOut).toString());
        }
        else {
            System.out.println(HotelResource.getInstance().findARoom(checkIn, checkOut).toString());
        }
        System.out.println("Which room would you like to reserve?(Please enter room number:)");
        String roomNumber = kb.nextLine();
        //Book the room.

        try {
            HotelResource.getInstance().bookARoom(email, HotelResource.getInstance().getRoom(roomNumber), checkIn, checkOut);
            if ( HotelResource.getInstance().getCustomersReservation(email) == null){
               System.out.println("Please choose a room from the list of available rooms.");
                return;
            }
            else{
                //Mark the room as no longer free
                Room bookedRoom = (Room) HotelResource.getInstance().getRoom(roomNumber);
                bookedRoom.setBusy();
                System.out.println("Room " + roomNumber + " successfully reserved. We look forward to seeing you on " + checkInDate);

            }
        }
        catch (NullPointerException e) {
            System.out.println ("Room " + roomNumber + " does not exist. Returning to main menu...");

        }
        catch (ConcurrentModificationException ce){
            System.out.println("Concurrent modification exception : This room is already booked. "+ ce);

        }
    }


    public static void main (String [] args) {

    }
}
