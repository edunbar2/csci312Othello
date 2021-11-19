package edu.unca.csci312;

import java.util.Comparator;

public class Move{

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
        if(this.pass)
            return "P";
        String ret = "";
        int pipe = position;
        int c = Integer.parseInt(Integer.toString(pipe).substring(1));
        String row = "";
        String column = "";
        pipe -= c;
        pipe = pipe/10;
        row += Integer.toString((pipe));
        switch(c) {
            case 1:
                column += "a ";
                break;
            case 2:
                column += "b ";
                break;
            case 3:
                column += "c ";
                break;
            case 4:
                column += "d ";
                break;
            case 5:
                column += "e ";
                break;
            case 6:
                column += "f ";
                break;
            case 7:
                column += "g ";
                break;
            case 8:
                column += "h ";
                break;
            default:
                break;
        }
        ret += column + row;

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
