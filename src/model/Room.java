package model;

public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType enumeration;

    public String toString(){
        return "Room number " + roomNumber + "Price" + price + "Room type " + enumeration;
    }

    public String getRoomNumber(){
        return roomNumber;
    }

    public Double getRoomPrice(){
        return price;
    }

    @Override
    public RoomType getRoomType(){
        return enumeration;
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
        this.enumeration = enumeration;
    }

}
