package api;

import model.Room;
 import java.util.Scanner;
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
                        break;
                    }


                    System.out.println("Please enter check in date(format MMM-dd-yyyy):");
                    String checkInDate = kb.nextLine();
                    System.out.println("Please enter check out date (format MMM-dd-yyyy):");
                    String checkOutDate = kb.nextLine();


                    try {
                        checkIn = formatter.parse(checkInDate);
                        checkOut = formatter.parse(checkOutDate);



                    } catch (ParseException e) {
                        System.out.println("Please enter valid dates in the format MMM-dd-yyyy");
                        break;
                    }

                    if (HotelResource.getInstance().findARoom(checkIn, checkOut).isEmpty()) {
                        System.out.println("No room available for the dates selected.");
                    } else {
                        //Add this customer to the list of customers.

                        System.out.println(HotelResource.getInstance().findARoom(checkIn, checkOut).toString());
                        System.out.println("Which available room would you like to reserve?(Please enter room number:)");
                        String roomNumber = kb.nextLine();
                        //Book the room.

                        try {
                            HotelResource.getInstance().bookARoom(email, HotelResource.getInstance().getRoom(roomNumber), checkIn, checkOut);

                            //Mark the room as no longer free
                            Room bookedRoom = (Room) HotelResource.getInstance().getRoom(roomNumber);
                            bookedRoom.setFree();
                        }
                        catch (NullPointerException e) {
                            System.out.println ("Room " + roomNumber + " does not exist. Returning to main menu...");
                            break;
                        }
                        System.out.println("Room " + roomNumber + " successfully reserved! We look forward to seeing you on " + checkInDate);

                    }
                    break;
                case 2:
                    kb.nextLine();
                    //2. See my reservations
                    //Check that customer has an account first before trying to access their reservation.
                    System.out.println("Please enter your email address:");
                    String customerEmail = kb.nextLine();

                    //call the hotel resource customer reservation method.
                    if (HotelResource.getInstance().getCustomersReservation(customerEmail).isEmpty()){//Check if there is no reservation.
                        System.out.println("No reservation was made using this email address:" + customerEmail);
                    }
                    else {
                        //Get the customer's reservation using their email.
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
                    HotelResource.getInstance().CreateACustomer(firstName, lastName, emailAddress);
                    System.out.println("Account for customer " + firstName + " " + lastName + " successfully created");
                    break;

                case 4:
                    kb.nextLine();
                    //switch to admin menu here
                    AdminMenu adminMenu = new AdminMenu();
                    adminMenu.start();
                    break;
                case 5:
                    //Exit the application
                    kb.nextLine();
                    System.out.println("Exiting Main menu.....");
                    keepRun = false;
                    System.exit(0);
                    break;


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
