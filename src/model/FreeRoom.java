package model;

public class FreeRoom extends Room{



    public FreeRoom(){
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
