package api;

import model.IRoom;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

public class AdminMenu {

    //Crate scanner to read user input.
    Scanner kb = new Scanner(System.in);
    Date checkIn ;
    Date checkOut ;
    int action = 0;
    boolean keepRunning = true;

    //Date input formatter
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    //Get the user input
    public int getAction(){
        System.out.println("Please enter a number for the admin menu item:");
        int response = kb.nextInt();
        return response;
    }
    public void start(){
        while (keepRunning) {
            action = getAction();
        }



        switch (action){
            case 1:
                //See all customers;
                System.out.println(AdminResource.getInstance().getAllCustomers().toString());
                break;

            case 2:
                //See all rooms
                System.out.println (AdminResource.getInstance().getAllRooms().toString());
                break;

            case 3:
                //See all reservation
                AdminResource.getInstance().displayAllReservations();
                break;

            case 4:
                //Add a room
                List<IRoom> rooms = new LinkedList<IRoom>();
                AdminResource.getInstance().addRooms(rooms);
                System.out.println("Room" );
                break;
                
            case 5:
                MainMenu mainMenu = new MainMenu();
                mainMenu.startActions();
                break;

            case 6:
                keepRunning = false;
                break;
        }
    }

    public static void main (String [] args) {
        System.out.println("Admin menu:" +
                "\n" + "1. See all Customers" + "\n" +
                "2. See all Rooms" + "\n" +
                "3. See all Reservations" + "\n" +
                "4. Add a Room" + "\n" +
                "5. Back to Main Menu" + "\n");
    }
}
