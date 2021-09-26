package model;
import java.util.Date;

public class Reservation {
    private Customer customer = new Customer();
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
        return "Customer: "  + this.getCustomer().getFirstName() +" "+ this.getCustomer().getLastName() + "Room" + room + " Check in date" + checkInDate + " check out date " + checkOutDate + "\n";
    }
}
