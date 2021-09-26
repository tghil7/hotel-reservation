package api;
 import java.text.ParseException;
 import java.util.Scanner;
 import java.io.*;
 import service.ReservationService;
 import service.CustomerService;
 import java.util.Date;
 import java.util.*;
 import java.text.*;

public class MainMenu {

    //Crate scanner to read user input.
    Scanner kb = new Scanner(System.in);
    Date checkIn ;
    Date checkOut ;
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

      int response = kb.nextInt();
      return response;
    }
    public void startActions() {
        while (keepRun) {
            action = getAction();//collect input


            switch (action) {
                case 1:
                    kb.nextLine();
                    System.out.println("Please enter your email address:");
                    String email = kb.nextLine();
                    System.out.println("Please enter check in date(format MMM-dd-yyyy):");
                    String checkInDate = kb.nextLine();
                    System.out.println("Please enter check out date (format MMM-dd-yyyy):");
                    String checkOutDate = kb.nextLine();


                    try {
                        checkIn = formatter.parse(checkInDate);
                        checkOut = formatter.parse(checkOutDate);

                        System.out.println(HotelResource.getInstance().findARoom(checkIn, checkOut).toString());
                    } catch (ParseException e) {
                        System.out.println("Parsing exception" + e);
                    }

                    if (HotelResource.getInstance().findARoom(checkIn, checkOut).isEmpty()) {
                        System.out.println("No room available for the dates selected.");
                    } else {
                        System.out.println("Which available room would you like to reserve?(Please enter room number:)");
                        String roomNumber = kb.nextLine();
                        //Book the room.
                        HotelResource.getInstance().bookARoom(email, HotelResource.getInstance().getRoom(roomNumber), checkIn, checkOut);
                        //Set the room to busy
                        HotelResource.getInstance().getRoom(roomNumber).setRoomStatus(false);
                        System.out.println("Room " + roomNumber + " successfully reserved! We look forward to seeing you on " + checkInDate);
                    }
                    break;
                case 2:
                    kb.nextLine();
                    //2. See my reservations
                    System.out.println("Please enter your email address:");
                    String customerEmail = kb.nextLine();
                    //call the hotel resource customer reservation method.
                    if (HotelResource.getInstance().getCustomersReservation(customerEmail).isEmpty()){//Check if there is no reservation.
                        System.out.println("No reservation was made using this email address:" + customerEmail);
                    }
                    else {
                        System.out.println(HotelResource.getInstance().getCustomersReservation(customerEmail).toString());
                    }

                    break;
                //...other case statements
                case 3:
                    //3. Create an account
                    kb.nextLine();
                    System.out.println("Please enter your first name");
                    String firstName = kb.nextLine();
                    System.out.println("Please enter your last name");
                    String lastName = kb.nextLine();
                    System.out.println("Please enter your email address:");
                    String emailAddress = kb.nextLine();
                    HotelResource.getInstance().CreateACustomer(emailAddress, firstName, lastName);
                    System.out.println("Account for customer " + firstName + " " + lastName + " successfully created");
                    break;

                case 4:
                    kb.nextLine();
                    //switch to admin menu here
                    AdminMenu adminMenu = new AdminMenu();
                    adminMenu.start();
                    break;
                case 5:
                    kb.nextLine();
                    System.out.println("Exiting Admin menu.....");
                    keepRun = false;
                    return;


            }
        }
    }
    public static void main (String [] args) {
        System.out.println("Main menu:" +
                "\n" + "1. Find and reserve a room" + "\n" +
                "2. See my reservations" + "\n" +
                "3. Create an account" + "\n" +
                "4. Admin" + "\n" +
                "5. Exit" + "\n");
    }
}
