package model;

public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType roomType;
    private boolean roomFree;

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
        if (roomFree){
            return true;
        }
        else{
            return false;
        }
    }

    public void setRoomStatus(boolean value){
        if(value) {
            roomFree = true;
        }

        else{
            roomFree = false;
        }

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
