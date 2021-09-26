package model;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
