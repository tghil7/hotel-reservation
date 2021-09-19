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
    //Get the user input
    public int getAction(){
      System.out.println("Please enter a number for the menu item to be accessed");

      int response = kb.nextInt();
      return response;
    }
    public void startActions(){
        int action = getAction();//collect input
        switch(action){
            case 1:
                System.out.print ("Please enter check in date");
                String checkInDate = kb.nextLine();
                try{
                  Date checkIn = checkInDate.parse(checkInDate);
                }
                catch(ParseException e){
                    System.out.println("Parsing exception"+ e);
                }
                HotelResource.getInstance().findARoom();
                break;
            case 2:
                action2();
                break;
        //...other case statements
            case 5:
                //switch to admin menu here
                AdminMenu aMenu = new AdminMenu();
                aMenu.start();
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
