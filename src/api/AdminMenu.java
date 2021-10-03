package api;

import model.IRoom;
import model.RoomType;
import model.Room;
import java.text.ParseException;
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
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Admin menu:" +
                "\n" + "1. See all Customers" + "\n" +
                "2. See all Rooms" + "\n" +
                "3. See all Reservations" + "\n" +
                "4. Add a Room" + "\n" +
                "5. Back to Main Menu" + "\n");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Please enter a number for the admin menu item:");
        int response = kb.nextInt();
        return response;
    }
    public void start(){
        while (keepRunning) {
            action = getAction();




        switch (action){
            case 1:
                kb.nextLine();
                //See all customers;
                if(AdminResource.getInstance().getAllCustomers().isEmpty()){
                   System.out.println("No customer added yet.");
                }
                else {
                    System.out.println(AdminResource.getInstance().getAllCustomers().toString());
                }
                break;

            case 2:
                kb.nextLine();
                //See all rooms
                if(AdminResource.getInstance().getAllRooms().isEmpty()){
                    System.out.println("No room added yet. Please use menu 4 to add a room!");
                }
                System.out.println (AdminResource.getInstance().getAllRooms().toString());
                break;

            case 3:
                kb.nextLine();
                System.out.println("Current reservations:");
                //See all reservations
                AdminResource.getInstance().displayAllReservations();
                break;

            case 4:
                //Add a room
                kb.nextLine();
                //The Admin resource addRoom method takes a room list as argument.
                List<IRoom> rooms = new LinkedList<IRoom>();
                //The newly created room would need to be added  to the list of rooms, before that list is passed.
                System.out.println("Please enter the room number:");
                String roomNumber = getUserInputForRoom();
                System.out.println("Please enter the room price:");
                Double price = getUserInputForPrice();
                kb.nextLine();

                //Try to get the room type from the user input
                System.out.println("Please enter the room type (Enter exactly either 'SINGLE' or 'DOUBLE':");
                RoomType roomType = null;
                //Check for valid input:
                roomType = getMyRoomType();
                //Set the room free by default

                //Create the new room with the information from the user, and add it to the list.
                IRoom newRoom = new Room(roomNumber, price, roomType, true);
                rooms.add (newRoom);
                AdminResource.getInstance().addRooms(rooms);
                System.out.println("Room " + roomNumber + " successfully added!" );
                break;
                
            case 5:
                kb.nextLine();
                MainMenu mainMenu = new MainMenu();
                mainMenu.startActions();
                break;


           }
        }
    }

    public String getUserInputForRoom(){
        while (true) {
            try {
                String userInput = kb.nextLine();
                int input = 0;
                Integer.parseInt(userInput);
                return userInput;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number:");

            }
        }
    }

    public Double getUserInputForPrice(){
        while (true) {
            try {
                String userInput = kb.nextLine();
                Double input = 0.00;
                input = Double.parseDouble(userInput);
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number of type double:");
            }
        }
    }

    public RoomType getMyRoomType(){
        while (true){
            try {
                String thisRoomType = kb.nextLine();
                RoomType type = RoomType.valueOf(thisRoomType);
                return type;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter exactly either 'SINGLE' or 'DOUBLE':");

            }
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
