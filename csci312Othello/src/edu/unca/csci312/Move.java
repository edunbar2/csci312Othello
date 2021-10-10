package edu.unca.csci312;

public class Move {

    //instance variables
    private int position;
    private String move;
    private boolean pass;
    //constructors
    public Move(int position) {
        this.position = position;
        this.pass = false;
    }

    public Move(String move) {
        if(move == "P") pass = true;
    }

    public String printMove() {
        String ret = "";
        if(pass) return "Pass";
        int temp = position % 10;
        switch(temp){
            case 1:
                ret += "A";
            case 2:
                ret += "B";
            case 3:
                ret += "C";
            case 4:
                ret += "D";
            case 5:
                ret += "E";
            case 6:
                ret += "F";
            case 7:
                ret += "G";
            case 8:
                ret += "H";
        }
        temp = position/10;
        ret += temp;
        return ret;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public boolean isPass() {
        return pass;
    }

}
