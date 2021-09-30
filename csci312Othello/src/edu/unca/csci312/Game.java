package edu.unca.csci312;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class Game {
    // constants
    public static final int player = 1;
    public static final int opponent = -1;
    public static final int White = 128;
    public static final int Black = 256;

    // variables
    public static Board gameboard;
    public static int currentPlayer; // -1 or 1
    public static int myColor;
    public static int opponentColor;
    public static double playerTimer;
    public static double opponentTimer;

    public static void main(String[] args) {
        runGame();
    }


    public static void runGame() {
        playerTimer = 0.0;
        opponentTimer = 0.0;
        //timer for individual moves
        double moveTime;
        Random ran = new Random();
        boolean moveMade = false;
        // get user input
        while (myColor != Black && myColor != White) {
            myColor = getInput("What color do you want to play?");
        }
        //set opponent color
        if(myColor == Black) opponentColor = White;
        else opponentColor = Black;

        // create game board
        gameboard = new Board(myColor);
        //set player turn
        if (myColor == Black)
            currentPlayer = player;
        else
            currentPlayer = opponent;

        while (!gameOver()) {
            System.out.printf("(C) Player time: %f\n", playerTimer);
            System.out.printf("(C) Opponent time: %f\n", opponentTimer);

            gameboard.printBoard();
            if (currentPlayer == player) {
                moveTime = System.currentTimeMillis()/1000.0;
                Stack<Move> moves = gameboard.generateMoves(myColor);
                int move = getInput("What move do you want to make?");
                //int move = moves.elementAt(ran.nextInt(moves.size())).getPosition();
                moveMade = gameboard.applyMove(move, myColor, moves);
                moveTime = (System.currentTimeMillis()/1000.0)-moveTime;
                playerTimer += moveTime; //add move time to timer
                System.out.printf("(C) %d", gameboard.getBlackPieces());

            }else if(currentPlayer == opponent) {
                moveTime = System.currentTimeMillis()/1000.0;
                Stack<Move> moves = gameboard.generateMoves(opponentColor);
                int move = moves.elementAt(ran.nextInt(moves.size())).getPosition();
                moveMade = gameboard.applyMove(move, opponentColor, moves);
                moveTime = (System.currentTimeMillis()/1000.0)-moveTime;
                opponentTimer += moveTime; //add time taken to timer
            }
            if(moveMade) {
                currentPlayer *= -1;
                moveMade = false;
            }


        }
        gameboard.printBoard();
        System.out.println("Game over!" );
        int remainingBlack = gameboard.getBlackPieces();
        int remainingWhite = gameboard.getWhitePieces();
        System.out.println("Black pieces remaining: " + remainingBlack);
        if(playerTimer >= 90.0)
            System.out.println(opponentColor + " Wins!");
        else if(opponentTimer >= 90.0)
            System.out.println(myColor + " Wins");
        else if(remainingBlack > remainingWhite)
            System.out.println("Black wins!");
        else
            System.out.println("White wins!");
    }

    public static boolean gameOver() {
        boolean endGame = false;
        int count = 0;
        for(int i = 0; i < 100; i++) {
            if(gameboard.getBoard()[i] == 0) {
                count++;
            }
        }

        if(count == 0 || gameboard.getWhitePieces() == 0 || gameboard.getBlackPieces() == 0)
            endGame = true;
        Stack<Move> tempBlack = gameboard.generateMoves(Black);
        Stack<Move> tempWhite = gameboard.generateMoves(White);
        if(tempBlack.pop().isPass() && tempWhite.pop().isPass())
            endGame = true;
        //if(playerTimer >= 90.0 || opponentTimer >= 90.0)
        //endGame = true;

        return endGame;
    }

    public static int getInput(String question) {
        Scanner input = new Scanner(System.in);
        int ret = -1;
        while (ret == -1) {
            System.out.println(question);
            String temp = input.nextLine();
            ret = interpretInput(temp);
        }
        if(ret == 0){
            System.out.println("Comment Logged");
            ret = getInput(question);
        }
        return ret;
    }

    /**
     * This function takes in a string and interprets it as a comment, game move, or
     * initialization
     *
     * @param input to be interpreted
     * @return integer representation of input
     */
    public static int interpretInput(String input) {
        int ret = 0;
        // check for comment
        if (input.length() >= 3 && input.substring(0, 2).equals("(C)"))
            return ret;

        // check for initialization keys
        // NOTE checking for if(input == "I B") resulted in false every time
        // reason unknown
        if (input.charAt(0) == 'I') {
            if (input.charAt(input.length() - 1) == 'B')
                return Black; // initialize to black
            else if (input.charAt(input.length() - 1) == 'W')
                return White; // initialize to white
        }

        // interpret movements
        if (input.charAt(0) == 'A')
            ret = 1;
        else if (input.charAt(0) == 'B')
            ret = 2;
        else if (input.charAt(0) == 'C')
            ret = 3;
        else if (input.charAt(0) == 'D')
            ret = 4;
        else if (input.charAt(0) == 'E')
            ret = 5;
        else if (input.charAt(0) == 'F')
            ret = 6;
        else if (input.charAt(0) == 'G')
            ret = 7;
        else if (input.charAt(0) == 'H')
            ret = 8;
        else {
            System.out.println("Invalid input, please try again.\n");
            return -1;
        }

        StringBuilder temp = new StringBuilder();
        for (int i = 1; i < input.length(); i++) {
            if(input.charAt(i) >= '0' && input.charAt(i) <= '9')
                temp.append(input.charAt(i));
        }
        ret += Integer.parseInt(temp.toString()) * 10;
        return ret;
    }

}

