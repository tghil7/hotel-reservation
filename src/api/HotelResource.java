package api;
import service.CustomerService;
import service.ReservationService;
import model.Customer;
import model.*;
import java.util.Date;
import java.util.Collection;

public final class HotelResource {

    private HotelResource(){}
    private static HotelResource  hotelResource= new HotelResource();
    public static HotelResource getInstance(){
        return hotelResource;
    }

      public Customer getCustomer(String email){
             return CustomerService.getInstance().getCustomer(email);
        }

        public void CreateACustomer( String firstName, String lastName, String email){
          CustomerService.getInstance().addCustomer(firstName,lastName,email);
        }

        public IRoom getRoom (String roomNumber){
          return ReservationService.getInstance().getARoom(roomNumber);
        }

        public Reservation bookARoom (String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
            return ReservationService.getInstance().reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
        }

        public Collection <Reservation> getCustomersReservation(String customerEmail){
          return ReservationService.getInstance().getCustomersReservation(getCustomer(customerEmail));
        }

        public Collection <IRoom> findARoom(Date checkIn, Date checkOut){
          return ReservationService.getInstance().findRooms(checkIn,checkOut);
        }
}
