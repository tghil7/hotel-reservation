package model;

public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType roomType;
    private boolean free;


    //Add a constructor?

    public Room (){

    }
    public Room (String roomNumber, Double price, RoomType roomType, boolean free){
        this.roomType= roomType;
        this.roomNumber = roomNumber;
        this.price = price;
        this.free = true;
    }

    public String toString(){
        String print = "";
     if (isFree()){
         print = "Room number " + roomNumber + " Price:  $" + 0.00 + " Room type " + roomType;
        }
     else{
         print = "Room number " + roomNumber + " Price:  $" + price + " Room type " + roomType;
     }
     return print;
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
       return free;
    }

    public boolean setFree(){
        if(free){
            free = false;
        }
        else {
            free = true;
        }
        return free;
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
