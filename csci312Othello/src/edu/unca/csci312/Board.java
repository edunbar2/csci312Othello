package edu.unca.csci312;

import java.util.Arrays;
import java.util.Stack;

public class Board {

    //pressuremap
    private static final int[] map =
            {
                    -20,-20,-20,-20,-20,-20,-20,-20,-20,-20,
                    -20, 10,  2,  8,  8 , 8 , 8 , 2 ,10,-20,
                    -20,  2,  1,  3,  3,  3,  3,  1,  2,-20,
                    -20,  8,  3,  6,  6,  6,  6,  3,  8,-20,
                    -20,  8,  3,  6,  7,  7,  6,  3,  8,-20,
                    -20,  8,  3,  6,  7,  7,  6,  3,  8,-20,
                    -20,  8,  3,  6,  6,  6,  6,  3,  8,-20,
                    -20,  2,  1,  3,  3,  3,  3,  1,  2,-20,
                    -20, 10,  2,  8,  8,  8,  8,  2, 10,-20,
                    -20,-20,-20,-20,-20,-20,-20,-20,-20,-20
            };

    // constants
    private static final int startWhite = 44; // tile D4
    private static final int startBlack = 45; // tile D5
    private static final int player = 1;
    private static final int opponent = -1;
    // values 128 and 256 used to represent black and white.
    private static final int White = 128;
    private static final int Black = 256;

    // instance variables
    private int[] board;
    private int playerColor;
    private int opponentColor;


    // constructors
    public Board(int playerColor) {
        this.playerColor = playerColor;
        if(this.playerColor == White) this.opponentColor = Black;
        else this.opponentColor =White;
        this.board = new int[100];
        initialize(this.playerColor);
        if(this.playerColor == Black){
            System.out.println("R B");
        }else if(this.playerColor == White){
            System.out.println("R W");
        }
    }

    public Board(Board copy) {
        this.playerColor = copy.getPlayerColor();
        this.board = new int[100];
        System.arraycopy(copy.board,0, this.board, 0, 100);

    }

    /**
     * search board for all legal moves and add them to stack.
     *
     * @param color of tile being searched
     * @return Stack containing legal moves
     */
    public Stack<Move> generateMoves(int color) {
        Stack<Move> moves = new Stack<Move>();
        // get players moves
        if (color == this.playerColor) {
            for (int i = 11; i < 89; i++) {
                if (board[i] == 1) {
                    Move move1 = new Move(expand(i, "NW", 1));
                    Move move2 = new Move(expand(i, "N", 1));
                    Move move3 = new Move(expand(i, "NE", 1));
                    Move move4 = new Move(expand(i, "E", 1));
                    Move move5 = new Move(expand(i, "SE", 1));
                    Move move6 = new Move(expand(i, "S", 1));
                    Move move7 = new Move(expand(i, "SW", 1));
                    Move move8 = new Move(expand(i, "W", 1));
                    if (move1.getPosition() != -1)
                        moves.push(move1);
                    if (move2.getPosition() != -1)
                        moves.push(move2);
                    if (move3.getPosition() != -1)
                        moves.push(move3);
                    if (move4.getPosition() != -1)
                        moves.push(move4);
                    if (move5.getPosition() != -1)
                        moves.push(move5);
                    if (move6.getPosition() != -1)
                        moves.push(move6);
                    if (move7.getPosition() != -1)
                        moves.push(move7);
                    if (move8.getPosition() != -1)
                        moves.push(move8);

                }
            }

            // get opponents moves
        } else {
            for (int i = 11; i < 89; i++) {
                if (board[i] == -1) {
                    Move move1 = new Move(expand(i, "NW", -1));
                    Move move2 = new Move(expand(i, "N", -1));
                    Move move3 = new Move(expand(i, "NE", -1));
                    Move move4 = new Move(expand(i, "E", -1));
                    Move move5 = new Move(expand(i, "SE", -1));
                    Move move6 = new Move(expand(i, "S", -1));
                    Move move7 = new Move(expand(i, "SW", -1));
                    Move move8 = new Move(expand(i, "W", -1));
                    if (move1.getPosition() != -1)
                        moves.push(move1);
                    if (move2.getPosition() != -1)
                        moves.push(move2);
                    if (move3.getPosition() != -1)
                        moves.push(move3);
                    if (move4.getPosition() != -1)
                        moves.push(move4);
                    if (move5.getPosition() != -1)
                        moves.push(move5);
                    if (move6.getPosition() != -1)
                        moves.push(move6);
                    if (move7.getPosition() != -1)
                        moves.push(move7);
                    if (move8.getPosition() != -1)
                        moves.push(move8);
                }
            }

        }
        if (moves.size() == 0) {
            Move pass = new Move("P");
            moves.push(pass);
        }

        return moves;
    }

    /**
     * expand in the given direction looking for a legal move
     *
     * @param pos on board to start search
     * @return position of legal move on array, -1 if no legal move in that
     *         direction
     */
    private int expand(int pos, String direction, int player) {
        int p = pos;
        int opponent = player * -1;
        int ret = -1;
        if (direction == "NW") {
            while (board[p] != -2) {
                    p = getNW(p);
                // illegal moves
                if (board[p] == player) {
                    ret = -1;
                    break;
                } else if (board[p] == 0 && ret != -1) { // end case
                    ret = p;
                    break;
                } else if (board[p] == opponent) { // search for legal move
                    ret = p;
                } else {
                    ret = -1;
                    break;
                }
            }
        } else if (direction == "N") {
            while (board[p] != -2) {
                p = getN(p);
                // illegal moves
                if (board[p] == player) {
                    ret = -1;
                    break;
                } else if (board[p] == 0 && ret != -1) { // end case
                    ret = p;
                    break;
                } else if (board[p] == opponent) { // search for legal move
                    ret = p;

                } else {
                    ret = -1;
                    break;
                }
            }
        } else if (direction == "NE") {
            while (board[p] != -2) {
                    p = getNE(p);
                // illegal moves
                if (board[p] == player) {
                    ret = -1;
                    break;
                } else if (board[p] == 0 && ret != -1) { // end case
                    ret = p;
                    break;
                } else if (board[p] == opponent) { // search for legal move
                    ret = p;
                } else {
                    ret = -1;
                    break;
                }
            }
        } else if (direction == "E") {
            while (board[p] != -2) {
                p = getE(p);
                // illegal moves
                if (board[p] == player) {
                    ret = -1;
                    break;
                } else if (board[p] == -2) { // illegal move
                    ret = -1;
                    break;
                } else if (board[p] == 0 && ret != -1) { // end case
                    ret = p;
                    break;
                } else if (board[p] == opponent) { // search for legal move
                    ret = p;

                } else {
                    ret = -1;
                    break;
                }
            }
        } else if (direction == "SE") {
            while (board[p] != -2) {
                p = getSE(p);
                // illegal moves
                if (board[p] == player) {
                    ret = -1;
                    break;
                } else if (board[p] == -2) { // illegal move
                    ret = -1;
                    break;
                } else if (board[p] == 0 && ret != -1) { // end case
                    ret = p;
                    break;
                } else if (board[p] == opponent) { // search for legal move
                    ret = p;

                } else {
                    ret = -1;
                    break;
                }
            }
        } else if (direction == "S") {
            while (board[p] != -2) {
                p = getS(p);
                // illegal moves
                if (board[p] == player) {
                    ret = -1;
                    break;
                } else if (board[p] == -2) { // illegal move
                    ret = -1;
                    break;
                } else if (board[p] == 0 && ret != -1) { // end case
                    ret = p;
                    break;
                } else if (board[p] == opponent) { // search for legal move
                    ret = p;
                } else {
                    ret = -1;
                    break;
                }
            }
        } else if (direction == "SW") {
            while (board[p] != -2) {
                p = getSW(p);
                // illegal moves
                if (board[p] == player) {
                    ret = -1;
                    break;
                } else if (board[p] == -2) { // illegal move
                    ret = -1;
                    break;
                } else if (board[p] == 0 && ret != -1) { // end case
                    ret = p;
                    break;
                } else if (board[p] == opponent) { // search for legal move
                    ret = p;
                } else {
                    ret = -1;
                    break;
                }
            }
        } else if (direction == "W") {
            while (board[p] != -2) {
                p = getW(p);
                // illegal moves
                if (board[p] == player) {
                    ret = -1;
                    break;
                } else if (board[p] == -2) { // illegal move
                    ret = -1;
                    break;
                } else if (board[p] == 0 && ret != -1) { // end case
                    ret = p;
                    break;
                } else if (board[p] == opponent) { // search for legal move
                    ret = p;
                } else {
                    ret = -1;
                    break;
                }
            }
        }
        if (board[p] == -2)
            ret = -1;

        return ret;
    }

    /**
     * This function checks if a move is legal and implements the move.
     *
     * @param pos    position on the board to place tile
     * @param player playing move
     */
    public boolean applyMove(int pos, int player) {
        //Generate moves for player
        Stack<Move> moves = this.generateMoves(player);
        // check stack for legal moves. may switch to priority queue for ease of access
        int index = -1;
        if(moves.elementAt(0).isPass()){
            System.out.println("(C) Passing");
            return true;
        }
        for (int i = 0; i < moves.size(); i++) {
            if (moves.elementAt(i).getPosition() == pos) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            if(player == playerColor)
                board[pos] = 1;
            else board[pos] = -1;

            if (player == playerColor)
                flipTiles(pos, 1);
            else
                flipTiles(pos, -1);

            return true; // notify game class of completion
        } else {
            System.out.println("(C) invalid move, try again");
            return false; // notify game class of invalid move
        }

    }

    /**
     * This function checks if lines are flip-able
     *
     * @param pos position on board to expand from
     * @param playerTile color of player tiles to flip to
     */
    private void flipTiles(int pos, int playerTile) {
        int opponentTile = playerTile * -1;
        int count = 0;

        // check northwest direction
        int temp = getNW(pos);
        Stack<Integer> move = new Stack<Integer>();
        while (board[temp] != -2 && board[temp] != 0 && board[temp] != playerTile) {
            move.push(temp);
            temp = getNW(temp);
        }
        if (board[temp] == playerTile && !move.isEmpty())
            flip(move);

        // ensure stack is empty
        move.clear();

        // check N direction
        temp = getN(pos);
        while (board[temp] != -2 && board[temp] != 0 && board[temp] != playerTile) {
            move.push(temp);
            temp = getN(temp);
        }
        if (board[temp] == playerTile && !move.isEmpty())
            flip(move);


        // ensure stack is clear
        move.clear();

        // get NE direction
        temp = getNE(pos);
        while (board[temp] != -2 && board[temp] != 0 && board[temp] != playerTile) {
            move.push(temp);
            temp = getNE(temp);
        }
        if (board[temp] == playerTile && !move.isEmpty())
            flip(move);

        //ensure stack is clear
        move.clear();

        // check E direction
        temp = getE(pos);
        while (board[temp] != -2 && board[temp] != 0 && board[temp] != playerTile) {
            move.push(temp);
            temp = getE(temp);
        }
        if (board[temp] == playerTile && !move.isEmpty())
            flip(move);


        // ensure stack is clear
        move.clear();

        // check SE direction
        temp = getSE(pos);
        while (board[temp] != -2 && board[temp] != 0 && board[temp] != playerTile) {
            move.push(temp);
            temp = getSE(temp);
        }
        if (board[temp] == playerTile && !move.isEmpty())
            flip(move);


        // ensure stack is clear
        move.clear();

        // check S direction
        temp = getS(pos);
        while (board[temp] != -2 && board[temp] != 0 && board[temp] != playerTile) {
            move.push(temp);
            temp = getS(temp);
        }
        if (board[temp] == playerTile && !move.isEmpty())
            flip(move);


        // ensure stack is clear
        move.clear();

        // check SW direction
        temp = getSW(pos);
        while (board[temp] != -2 && board[temp] != 0 && board[temp] != playerTile) {
            move.push(temp);
            temp = getSW(temp);
        }
        if (board[temp] == playerTile && !move.isEmpty())
            flip(move);


        // ensure stack is clear
        move.clear();

        // check W direction
        temp = getW(pos);
        while (board[temp] != -2 && board[temp] != 0 && board[temp] != playerTile) {
            move.push(temp);
            temp = getW(temp);
        }
        if (board[temp] == playerTile && !move.isEmpty())
            flip(move);


        // ensure stack is clear
        move.clear();

    }

    private void flip(Stack<Integer> move) {
        while (!move.isEmpty()) {
            board[move.pop()] *= -1;
        }
    }

    // methods
    private void initialize(int playerColor) {
        for (int i = 0; i < 100; i++) { // set board to 0, boundaries to -2
            if (i % 10 == 0 || (i + 1) % 10 == 0 || i < 10 || i > 90 || i == 0) {
                board[i] = -2;
            } else {
                board[i] = 0;
            }

        }
        // initialize starting tiles to playerColor
        if (playerColor == White) {
            board[startWhite] = 1;
            board[getSE(startWhite)] = 1;
            board[getE(startWhite)] = -1;
            board[getS(startWhite)] = -1;
        } else { // playerColor = black
            board[startBlack] = 1;
            board[getSW(startBlack)] = 1;
            board[getW(startBlack)] = -1;
            board[getS(startBlack)] = -1;
        }
    }

    private int getN(int index) {
        return index - 10;
    }

    private int getE(int index) {
        return index + 1;
    }

    private int getS(int index) {
        return index + 10;
    }

    private int getW(int index) {
        return index - 1;
    }

    private int getNE(int index) {
        return index - 9;
    }

    private int getSE(int index) {
        return index + 11;
    }

    private int getSW(int index) {
        return index + 9;
    }

    private int getNW(int index) {
        return index - 11;
    }

    public int getPlayerColor() {
        return this.playerColor;
    }

    public int[] getBoard() {
        return this.board;
    }

    public int getBlackPieces() {
        int count = 0;
        for (int j : board) {
            if (j == 1 && playerColor == Black) count++;
            else if (j == -1 && playerColor == White) count++;
        }
        return count;
    }

    public int getWhitePieces() {
        int count = 0;
        for (int j : board) {
            if (j == 1 && playerColor == White) count++;
            else if (j == -1 && playerColor == Black) count++;
        }
        return count;
    }

    /**
     * This function takes in the board to be evaluated and adds up the score of the player.
     * This score is generated using the equation (Value of Board * Number of Moves) / Number of Opponent Moves
     * @param currentBoard to be evaluated
     * @return value of board
     */
    public int evaluate(Board currentBoard) {
        int boardValue = 0;
        for(int i = 11; i < 89; i++){ // get value of the current board
            if(i%10 != 0 && (i+1)%10 != 0){
                if(currentBoard.getBoard()[i] == opponent)
                    boardValue += map[i];
                else if(currentBoard.board[i] == player)
                    boardValue -= map[i];
            }
        }
         // get other data
        int numMoves;
        int opponentMoves;
        if(playerColor == White){
            numMoves = generateMoves(Black).size();
            opponentMoves = generateMoves(White).size();
        }else{
            numMoves = generateMoves(White).size();
            opponentMoves = generateMoves(Black).size();
        }


         // score equals boardValue * number of moves / number of opponent moves.
        int score = (boardValue * numMoves)/opponentMoves;
        if(Game.gameOver(currentBoard)){
            int blackPieces = currentBoard.getBlackPieces();
            int whitePieces = currentBoard.getWhitePieces();
            if(Game.getWinner(blackPieces, whitePieces) == opponentColor) score += 10000000;
            else score -= 10000000;
        }
        return score;
    }

    public void printBoard() {
        System.out.print("    A B C D E F G H\n  X X X X X X X X X X");
        for (int i = 10; i < board.length - 10; i++) {
            if (i % 10 == 0)
                System.out.print("\n"); // border has been reached
            if (i % 10 == 0 && i > 0 && i < 90)
                System.out.printf("%d ", i / 10);

            if (board[i] == 0)
                System.out.print("- ");
            else if (board[i] == 1) {
                if (playerColor == White)
                    System.out.print("W ");
                else
                    System.out.print("B ");
            } else if (board[i] == -1) {
                if (playerColor == Black)
                    System.out.print("W ");
                else
                    System.out.print("B ");
            } else if (board[i] == -2)
                System.out.print("X ");
        }
        System.out.println("\n  X X X X X X X X X X");
    }

}