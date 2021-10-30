package model;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public IRoom getRoom(){
        return room;
    }

    public Customer getCustomer(){
        return this.customer;
    }

    public Customer getCustomer (String email){
        //Get the list of customers, loop through that list and compare customers.
        //Return the one with the matching email.
        if(email == customer.getEmail()){
            customer.toString();
        }
        return customer;
    }

    public Date getCheckInDate(){
        return checkInDate;
    }

    public Date getCheckOutDate(){
        return checkOutDate;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public int hashCode(){
        return Objects.hash(customer,room,checkInDate, checkOutDate);
    }

    @Override
    public boolean equals(final Object obj){
        Reservation otherReservation = (Reservation) obj;
        return (room.equals(otherReservation.room) && checkInDate.equals(otherReservation.checkInDate) && checkOutDate.equals(otherReservation.checkOutDate) && customer.equals(otherReservation.customer));

    }

    public void setRoom(IRoom room) {
        this.room = room;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString(){
        customer = new Customer();
        SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy", Locale.ENGLISH);
        return room.toString() + " Check in date:" + checkInDate.toString() + " Check out date: " + checkOutDate.toString() + "\n";
    }
}
