package edu.unca.csci312;

import MonteCarloAI.MonteCarloSearch;

import java.util.*;

import static java.lang.System.exit;

public class Game {
    // constants
    public static final int player = 1;
    public static final int opponent = -1;
    public static final int White = 128;
    public static final int Black = 256;
    public static final double GameTime = 600;
    public static final double expectedMoves = 70.0;

    // variables
    public static Board gameboard;
    public static int currentPlayer; // -1 or 1
    public static int myColor;
    public static int opponentColor;
    public static double playerTimer;
    public static double opponentTimer;
    public static int movesmade;

    public static void main(String[] args) {
        runGame();
    }


    public static void runGame() {
        playerTimer = 0.0;
        opponentTimer = 0.0;
        movesmade = 0;
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

        while (!Board.gameOver(gameboard)) {
            System.out.printf("C Player time: %f\n", playerTimer);
            System.out.printf("C AI time: %f\n", opponentTimer);

            gameboard.printBoard();
            if (currentPlayer == player) {
                moveTime = System.currentTimeMillis()/1000.0;
                PriorityQueue<Move> moves = gameboard.generateMoves(myColor);
                int move = getInput("C What move do you want to make?");
                //int move = -2;
                /*for(int i = 0; i < ran.nextInt(moves.size()); i++){
                    move = moves.remove().getPosition();
                }*/

                if(move == -2){
                    Move node = new Move("P");
                    moves.add(node);
                    move = 0;
                }
                moveMade = gameboard.applyMove(move, myColor);
                moveTime = (System.currentTimeMillis()/1000.0)-moveTime;
                playerTimer += moveTime; //add move time to timer
                System.out.printf("C %d\n", gameboard.getBlackPieces());

            }else if(currentPlayer == opponent) {
                moveTime = System.currentTimeMillis()/1000.0;
                moveMade = getAIMove_MCTS(moveTime);
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



    public static int getInput(String question) {
        Scanner input = new Scanner(System.in);
        int ret = -1;
        while (ret == -1) {
            System.out.println(question);
            String temp = input.nextLine();
            if(temp.charAt(0) >= '0' && temp.charAt(0) <= '9'){
                if(Board.gameOver(gameboard)){
                    endGame();
                }
            }
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
            if(Board.gameOver(gameboard)){
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
        int winner = getWinner(remainingBlack, remainingWhite, true);
        if(winner == Black) System.out.println("C Black Wins!");
        else System.out.println("C White wins!");
        //tell other program to check for game over
        System.out.println(remainingBlack);
        exit(0);
    }

    public static int getWinner(int blackPieces, int whitePieces, boolean timing) {

        if (timing) {
           if (playerTimer >= GameTime)
              return opponentColor;
            else if (opponentTimer >= GameTime)
              return myColor;
        }

        if (blackPieces > whitePieces)
            return Black;
        else
            return White;
    }

    /**
     * This function uses the minimax algorithm to determine the best move
     * NOTE: this function currently does not work.
     * @param startTime Time when function started
     * @return best move according to Minimax with Alpha Beta
     */
    public static boolean getAIMove_AlphaBeta(double startTime){
        double currentTime = System.currentTimeMillis();
        int depth = 2;
        boolean moveMade = false;
         // generate current moves
        PriorityQueue<Move> moves = gameboard.generateMoves(opponentColor);
        double timePerMove = (GameTime / expectedMoves) / moves.size();
        //check for pass
        if(moves.size() == 1){
            if(moves.peek().isPass()){
                moveMade = gameboard.applyMove(-1, opponentColor);
                if(opponentColor == Black) System.out.println("B");
                else System.out.println("W");
                return moveMade;
            }else{
                moveMade = gameboard.applyMove(moves.remove().getPosition(), opponentColor);
                return moveMade;
            }
        }
         // determine best move
        int eval;
        Move bestMove = new Move("P");
        int highScore = Integer.MIN_VALUE;
        Iterator<Move> it = moves.iterator();
        while(it.hasNext()){
            Move move = it.next();
            int alpha = Integer.MIN_VALUE;
            int beta = Integer.MAX_VALUE;
            //iterative deepening
            currentTime = System.currentTimeMillis()/1000.0;
            double sigmaMT = System.currentTimeMillis()/1000.0 - currentTime;
            while(sigmaMT < timePerMove) {
                System.out.printf("C Searching move %s at depth: %d\n", move.printMove(), depth);
                eval = Integer.MIN_VALUE;

                eval = minimax(move, gameboard, depth, true, alpha, beta, currentTime, timePerMove);
                if (eval > highScore) {
                    highScore = eval;
                    bestMove = move;
                }else if(eval == Integer.MIN_VALUE){
                    System.out.printf("Search failed at Depth %d", depth);
                }
                //Null window search
                alpha = highScore;
                beta = alpha+1;
                depth += 2;
                sigmaMT += System.currentTimeMillis()/1000.0 - currentTime;
                System.out.printf("C SigmaMT = %.2f\n", sigmaMT);
            }
            depth = 2;
        }

        System.out.printf("C Best move is [%d] with score (%d)\n", bestMove.getPosition(), highScore);

         //apply best move

        moveMade = gameboard.applyMove(bestMove.getPosition(), opponentColor);


        String out = "";
        if(opponentColor == Black) out += "B ";
        else out += "W ";
        out += bestMove.printMove();
        System.out.println(out);

        //return moveMade
        return moveMade;


    }

    /**
     * This function uses the Monte Carlo Tree Search (MCTS) algorithm in order to find
     * the best move. This algorithm should be able to beat any Minimax-based AI.
     * @param startTime Time at function call
     * @return Best move according to MCTS algorithm
     */
    private static boolean getAIMove_MCTS(double startTime){
        movesmade++;
        double timeForMove = GameTime / expectedMoves;
        MonteCarloSearch monte = new MonteCarloSearch();
        Board tempBoard = new Board(gameboard);
        Move move;
        if(movesmade < 2)
            move = gameboard.generateMoves(opponentColor).remove();
        else if(gameboard.generateMoves(opponentColor).size() == 0){
            move = new Move("P");
        } else {
            int color = 0;
            if(opponentColor == Black)
                color = 1;
            else color = -1;
            move = monte.findNextMove(tempBoard, color);
        }
        if(!move.isPass()) {
            String out = "";
            if (opponentColor == Black)
                out += "B ";
            else out += "W ";
            System.out.println(out + move.printMove());
        }else {
           if(opponentColor == Black) System.out.println("B");
           else System.out.println("W");
        }
        Boolean ret = gameboard.applyMove(move.getPosition(), 1);



        return ret;
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
    private static int minimax(Move move, Board copy, int d, boolean minimax, int alpha, int beta, double startTime, double timePerMove) {
        if((System.currentTimeMillis()/1000) - startTime > timePerMove) {
            return Integer.MIN_VALUE;
        }
      //apply move
        Board tempBoard = new Board(copy);
        if(minimax)
            tempBoard.applyMove(move.getPosition(), opponentColor);
        else
            tempBoard.applyMove(move.getPosition(), myColor);
        // System.out.println("C Checking at depth: " + d + " out of " + depth);
        if(d == 0 || Board.gameOver(copy))
            return copy.evaluate(copy);


        if(minimax){
        int maxEval = Integer.MIN_VALUE;
          PriorityQueue<Move> moves = tempBoard.generateMoves(myColor);
         // System.out.println("C Moves to check at depth" + d +": " + moves.size());
          while(!moves.isEmpty()){
              int pos = moves.peek().getPosition();
              int eval = minimax(moves.remove(), tempBoard, d-1, false, alpha, beta, startTime, timePerMove);
             // System.out.printf("C Testing position (%d) with score %d \n", pos, eval);
              maxEval = Math.max(maxEval, eval);
              alpha = Math.max(alpha, maxEval);
              if(beta <= alpha) break;
          }
          return maxEval;
        }else{
            int minEval = Integer.MAX_VALUE;
            PriorityQueue<Move> moves = tempBoard.generateMoves(opponentColor);
           //System.out.println("Moves to check at depth" + d +": " + moves.size());
            while(!moves.isEmpty()){
                int pos = moves.peek().getPosition();
                int eval = minimax(moves.remove(), tempBoard, d-1, true, alpha, beta, startTime, timePerMove);
               // System.out.printf("C Testing position (%d) with score %d \n", pos, eval);
                minEval = Math.min(minEval, eval);
                beta = Math.min(minEval, beta);
                if(beta <= alpha) break;
            }
            return minEval;
        }
    }



}

