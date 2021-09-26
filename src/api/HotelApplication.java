package api;

public class HotelApplication {
    public static void main (String [] args){
    //The admin would need to add rooms before customers are able to make a reservation.
        MainMenu mainMenu = new MainMenu();
        mainMenu.startActions();
    }
}
