package model;

public class FreeRoom extends Room{



    public FreeRoom(String roomNumber, Double price, RoomType roomType){
        super.setRoomNumber(roomNumber);
        super.setEnumeration(roomType);
        super.setPrice(0.00);
    }

    @Override
    public boolean isFree(){
                return true;
    }

    @Override
    public String toString(){
        return super.toString() + " Room Free Status" + this.isFree();
    }
}
