package api;
import api.HotelResource;
import model.*;
import service.*;

import java.util.ArrayList;
import java.util.List;

public class HotelApplication {
    public static void main (String [] args){
     /*   IRoom room1 = new Room("101", 120.00, RoomType.SINGLE);
        IRoom room2 = new Room("102", 130.00, RoomType.DOUBLE);
        IRoom room3 = new Room("103", 130.00, RoomType.DOUBLE);
        List<IRoom> initialRooms = new ArrayList<>();
        if (initialRooms.isEmpty()) {
            initialRooms.add(room1);
            initialRooms.add(room2);
            initialRooms.add(room3);
        }
        AdminResource.getInstance().addRooms(initialRooms);*/
    //The admin would need to add rooms before customers are able to make a reservation.
        MainMenu mainMenu = new MainMenu();
        mainMenu.startActions();
    }
}
