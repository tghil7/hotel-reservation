package model;

public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType roomType;


    //Add a constructor?

    public Room (){

    }
    public Room (String roomNumber, Double price, RoomType roomType){
        this.roomType= roomType;
        this.roomNumber = roomNumber;
        this.price = price;
    }

    public String toString(){
        return "Room number " + roomNumber + " Price:  $" + price + " Room type " + roomType;
    }

    public String getRoomNumber(){
        return roomNumber;
    }

    public Double getRoomPrice(){
        return price;
    }

    @Override
    public RoomType getRoomType(){
        return roomType;
    }

    @Override
    public boolean isFree() {
       return true;
    }



    public void setRoomNumber(String roomNumber){
        this.roomNumber = roomNumber;
    }
    public void setPrice (Double price){
        this.price = price;
    }

    public void setEnumeration(RoomType enumeration) {
        this.roomType = enumeration;
    }

}
