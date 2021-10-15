package MontyCarloAI;

import edu.unca.csci312.*;

import java.util.Arrays;
import java.util.*;

public class State {
     // instance variables
    private Board board;
    private int player; // 1 or -1;
    private int visitCount;
    private double winScore;

     // constructors
    public State(Board board, int player){
        this.board = board;
        this.player = player;
        this.visitCount = 0;
        this.winScore = 0;
    }

     // getters and setters
    public Board getBoard(){
        return this.board;
    }
    public void setBoard(Board board){
        this.board = board;
    }

    public int getPlayer(){
        return this.player;
    }
    public void setPlayer(int player){
        this.player = player;
    }

    public int getVisitCount(){
        return this.visitCount;
    }
    public void setVisitCount(int visitCount){
        this.visitCount = visitCount;
    }

    public double getWinScore(){
        return this.winScore;
    }
    public void setWinScore(double winScore){
        this.winScore = winScore;
    }

    /**
     * This function generates all possible moves from the current state and applies them to boards.
     * Then it creates a list of states given these board and creates a list.
     * @return list of all possible States from current State
     */
    public ArrayList<State> getPossibleStates(){
        int color = 0;
        if(this.player == 1)
            color = this.board.getAIColor();
        else
            color = this.board.getPlayerColor();

        ArrayList<State> states = new ArrayList<State>();
        PriorityQueue<Move> moves = this.board.generateMoves(color);
        while(!moves.isEmpty()){
            Board tempBoard = new Board(this.board);
            tempBoard.applyMove(moves.remove().getPosition(), color);
            State state = new State(tempBoard, this.player * -1);
            states.add(state);
        }
        return states;
    }

    public void randomPlay(){
        int color = 0;
        if(this.player == 1)
            color = this.board.getAIColor();
        else
            color = this.board.getPlayerColor();
        PriorityQueue<Move> moves = this.board.generateMoves(color);
        Iterator<Move> it = moves.iterator();
        Random ran = new Random();
        int moveNumber = ran.nextInt(moves.size());
        Move move = new Move("P");
        for(int i = 0; i < moveNumber; i++){
            move = it.next();
        }
        this.board.applyMove(move.getPosition(), color);
    }
}
