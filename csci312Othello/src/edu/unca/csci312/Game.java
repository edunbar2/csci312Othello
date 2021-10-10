package edu.unca.csci312;

import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import static java.lang.System.exit;

public class Game {
    // constants
    public static final int player = 1;
    public static final int opponent = -1;
    public static final int White = 128;
    public static final int Black = 256;
    public static final int depth = 8;

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
        while (opponentColor != Black && opponentColor != White) {
            opponentColor = getInput("C What color do you want to play?");
        }
        //set color
        if(opponentColor == Black) myColor = White;
        else myColor = Black;

        // create game board
        gameboard = new Board(myColor);
        //set player turn
        if (myColor == Black)
            currentPlayer = player;
        else
            currentPlayer = opponent;

        while (!gameOver(gameboard)) {
            System.out.printf("C Player time: %f\n", playerTimer);
            System.out.printf("C AI time: %f\n", opponentTimer);

            gameboard.printBoard();
            if (currentPlayer == player) {
                moveTime = System.currentTimeMillis()/1000.0;
                Stack<Move> moves = gameboard.generateMoves(myColor);
                //int move = getInput("C What move do you want to make?");
                int move = moves.elementAt(0).getPosition();
                if(move == -2){
                    Move node = new Move("P");
                    moves.push(node);
                    move = 0;
                }
                moveMade = gameboard.applyMove(move, myColor);
                moveTime = (System.currentTimeMillis()/1000.0)-moveTime;
                playerTimer += moveTime; //add move time to timer
                System.out.printf("C %d\n", gameboard.getBlackPieces());

            }else if(currentPlayer == opponent) {
                moveTime = System.currentTimeMillis()/1000.0;
                moveMade =getAIMove();
                moveTime = (System.currentTimeMillis()/1000.0)-moveTime;
                opponentTimer += moveTime; //add time taken to timer
            }
            if(moveMade) {
                currentPlayer *= -1;
                moveMade = false;
            }


        }
        gameboard.printBoard();
        endGame();
    }

    public static boolean gameOver(Board board) {
        boolean endGame = false;
        int count = 0;
        for(int i = 0; i < 100; i++) {
            if(board.getBoard()[i] == 0) {
                count++;
            }
        }

        if(count == 0 || board.getWhitePieces() == 0 || board.getBlackPieces() == 0)
            endGame = true;
        Stack<Move> tempBlack = board.generateMoves(Black);
        Stack<Move> tempWhite = board.generateMoves(White);
        if(tempBlack.pop().isPass() && tempWhite.pop().isPass())
            endGame = true;
       // if(playerTimer >= 90.0 || opponentTimer >= 90.0)
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
            System.out.println("C Comment Logged");
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

        if(input.equals("n")){
            if(gameOver(gameboard)){
               endGame();
            }
        }
        int ret = 0;
        // check for comment
        if (input.charAt(0) == 'C')
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

        if(input.charAt(0) == 'B' && isCurrentPlayer(Black)){

            if(input.equals("B")){
                return -2; // tell system to pass
            }
            String interp = input.substring(2,input.length()).toUpperCase();
            // interpret movements
            if (interp.charAt(0) == 'A')
                ret = 1;
            else if (interp.charAt(0) == 'B')
                ret = 2;
            else if (interp.charAt(0) == 'C')
                ret = 3;
            else if (interp.charAt(0) == 'D')
                ret = 4;
            else if (interp.charAt(0) == 'E')
                ret = 5;
            else if (interp.charAt(0) == 'F')
                ret = 6;
            else if (interp.charAt(0) == 'G')
                ret = 7;
            else if (interp.charAt(0) == 'H')
                ret = 8;
            else {
                System.out.println("C Invalid input, please try again.\n");
                return -1;
            }
        }else if(input.charAt(0) == 'W' && isCurrentPlayer(White)){
            if(input.equals("W")){
                return -2; // tell system to pass
            }
            String interp = input.substring(2, input.length()).toUpperCase();
            // interpret movements
            if (interp.charAt(0) == 'A')
                ret = 1;
            else if (interp.charAt(0) == 'B')
                ret = 2;
            else if (interp.charAt(0) == 'C')
                ret = 3;
            else if (interp.charAt(0) == 'D')
                ret = 4;
            else if (interp.charAt(0) == 'E')
                ret = 5;
            else if (interp.charAt(0) == 'F')
                ret = 6;
            else if (interp.charAt(0) == 'G')
                ret = 7;
            else if (interp.charAt(0) == 'H')
                ret = 8;
            else {
                System.out.println("C Invalid input, please try again.\n");
                return -1;
            }
        }else{
            System.out.println("C Invalid input, please try again.\n");
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

    public static boolean isCurrentPlayer(int player){
        boolean isPlayer;
        if(currentPlayer == 1 && player == myColor)
            isPlayer = true;
        else if(currentPlayer == -1 && player == myColor)
            isPlayer = true;
        else isPlayer = false;


        return isPlayer;
    }



    public static void endGame(){
        System.out.println("C Game over!" );
        int remainingBlack = gameboard.getBlackPieces();
        int remainingWhite = gameboard.getWhitePieces();
        System.out.println("C Black pieces remaining: " + remainingBlack);
        int winner = getWinner(remainingBlack, remainingWhite);
        if(winner == Black) System.out.println("C Black Wins!");
        else System.out.println("C White wins!");
        //tell other program to check for game over
        System.out.println(remainingBlack);
        exit(0);
    }

    public static int getWinner(int blackPieces, int whitePieces) {

        //if (playerTimer >= 90.0)
            //return opponentColor;
        //else if (opponentTimer >= 90.0)
           // return myColor;
        if (blackPieces > whitePieces)
            return Black;
        else
            return White;
    }

    public static boolean getAIMove(){

        boolean moveMade = false;
         // generate current moves
        Stack<Move> moves = gameboard.generateMoves(opponentColor);
        //check for pass
        if(moves.size() == 1){
            if(moves.peek().isPass()){
                moveMade = gameboard.applyMove(-1, opponentColor);
                if(opponentColor == Black) System.out.println("B");
                else System.out.println("W");
                return moveMade;
            }else{
                moveMade = gameboard.applyMove(moves.pop().getPosition(), opponentColor);
                return moveMade;
            }
        }
         // determine best move
        int bestMove = 0;
        int highScore = Integer.MIN_VALUE;
        for(int i = 0; i < moves.size(); i++){
            int eval = minimax(moves.elementAt(i), gameboard, depth, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if(eval > highScore){
                highScore = eval;
                bestMove++;
            }
        }
        System.out.printf("C Best move is [%d] with score (%d)\n", moves.elementAt(bestMove).getPosition(), highScore);

         //apply best move

        moveMade = gameboard.applyMove(moves.elementAt(bestMove).getPosition(), opponentColor);
        //pipe move to Referee
        int pipe = moves.elementAt(bestMove).getPosition();
        int c = Integer.parseInt(Integer.toString(pipe).substring(1));
        String row = "";
        String column = "";
        pipe -= c;
        pipe = pipe/10;
        row += Integer.toString((pipe));
        switch(c){
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
            default: break;
        }

        String out = "";
        if(opponentColor == Black) out += "B ";
        else out += "W ";
        out += column + row;
        System.out.println(out);

        //return moveMade
        return moveMade;


    }

    /**
     * This algorithm recursively tests all possible lines
     * for the given move and returns the value of that move/line.
     * This algorithm also implements alpha beta pruning in order to save time
     * on lines that should never be picked.
     * @param move move to be applied/tested
     * @param copy of the board to be used
     * @param d depth at which to search
     * @param minimax determine whether it is the maximizing or minimizing players turn
     * @param alpha is the best value the maximizer can currently promise
     * @param beta is the best value the minimizer can currently promise
     * @return score for that move/line.
     */
    private static int minimax(Move move, Board copy, int d, boolean minimax, int alpha, int beta) {
      //apply move
        Board tempBoard = new Board(copy);
        if(minimax)
            tempBoard.applyMove(move.getPosition(), opponentColor);
        else
            tempBoard.applyMove(move.getPosition(), myColor);
        // System.out.println("C Checking at depth: " + d + " out of " + depth);
        if(d == 0 || gameOver(copy))
            return copy.evaluate(copy);


        if(minimax){
        int maxEval = Integer.MIN_VALUE;
          Stack<Move> moves = tempBoard.generateMoves(myColor);
         // System.out.println("C Moves to check at depth" + d +": " + moves.size());
          while(!moves.isEmpty()){
              int pos = moves.peek().getPosition();
              int eval = minimax(moves.pop(), tempBoard, d-1, false, alpha, beta);
             // System.out.printf("C Testing position (%d) with score %d \n", pos, eval);
              maxEval = Math.max(maxEval, eval);
              alpha = Math.max(alpha, maxEval);
              if(beta <= alpha) break;
          }
          return maxEval;
        }else{
            int minEval = Integer.MAX_VALUE;
            Stack<Move> moves = tempBoard.generateMoves(opponentColor);
           //System.out.println("Moves to check at depth" + d +": " + moves.size());
            while(!moves.isEmpty()){
                int pos = moves.peek().getPosition();
                int eval = minimax(moves.pop(), tempBoard, d-1, true, alpha, beta);
               // System.out.printf("C Testing position (%d) with score %d \n", pos, eval);
                minEval = Math.min(minEval, eval);
                beta = Math.min(minEval, beta);
                if(beta <= alpha) break;
            }
            return minEval;
        }
    }



}

