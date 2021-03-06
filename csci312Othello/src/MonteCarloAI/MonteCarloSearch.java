package MonteCarloAI;
/**
 * @Author Eric Dunbar
 * Inspiration from:
 * https://medium.com/@quasimik/monte-carlo-tree-search-applied-to-letterpress-34f41c86e238
 * https://www.baeldung.com/java-monte-carlo-tree-search
 * https://www.geeksforgeeks.org/ml-monte-carlo-tree-search-mcts/
 */

import edu.unca.csci312.*;

import java.util.ArrayList;
import java.util.Iterator;

public class MonteCarloSearch {
    private static final int WINSCORE = 10;
    private static final double endTime = 600.0 / 35.0;
    private int level;
    private int player; //1 or -1
    private static Tree gameTree;
    private Node currentNode;

    public MonteCarloSearch(Board currentBoard){
        if(Game.opponentColor == Game.White)
            this.player = 1;
        else this.player = -1;
        this.level = 0;
        gameTree = new Tree(currentBoard, player);
        State rootState = new State(null, currentBoard, player);
        Node rootNode = new Node(rootState);
        gameTree.setRoot(rootNode);
        currentNode = rootNode;

    }

    /**
     * This function powers the MCTS algorithm, following the selection, expansion
     * simulation, and backpropagation pattern.
     * @param currentBoard Current board in the game. This board is used to generate possible moves and to determine where the game is in relation to the game tree.
     * @param movePlayed move played to get to position in tree. This move is used to determine what position in the tree the game is at.
     * @return
     */
    public Move findNextMove(Board currentBoard, int movePlayed) {
        //find current position in tree
        if (currentNode.getState().getMove_to_state() != null || currentBoard.getAIColor() != Game.Black){ //if not first move
            if (currentNode.getChildren().size() == 0) { //check if children have been generated
                currentNode.generateChildren();
            }
            ArrayList<Node> currentChildren = currentNode.getChildren();
            for(int i = 0; i < currentChildren.size(); i++) { //check which move was played and pick corresponding node in tree
                if (currentChildren.get(i).getState().getMove_to_state().getPosition() == movePlayed){
                    currentNode = currentChildren.get(i);
                    break;
                }//if all nodes return false then move was a Pass, board did not change.
            }
        }
        double startTime = System.currentTimeMillis()/1000.0;
        while((((System.currentTimeMillis()/1000.0) - startTime)) < endTime){
           // System.out.println("C Time: " + (System.currentTimeMillis()/1000 - startTime));
            Node promisingNode = selectPromisingNode(currentNode);
            if(!Board.gameOver(promisingNode.getState().getBoard()))
                expandNode(promisingNode);
            Node nodeToExplore = promisingNode;
            if(promisingNode.getChildren().size() > 0){
                nodeToExplore = promisingNode.getRandomChildNode();
            }
            int playoutResult = simulateRandomPlayout(nodeToExplore);
            //backPropagate(nodeToExplore, playoutResult);
        }

        Node winnderNode = currentNode.getMaxChild();
        currentNode = winnderNode;
        return winnderNode.getState().getMove_to_state();
    }

    private void backPropagate(Node nodeToExplore, int playoutResult) {
        Node tempNode = nodeToExplore;
        while(tempNode != null){
            tempNode.getState().incrementVisitCount();
            if(tempNode.getState().getBoard().getAIColor() == playoutResult){
                tempNode.getState().addWinScore(WINSCORE);
            }
            tempNode = tempNode.getParent();
        }
    }

    /**
     * This function plays random moves down the game tree to determine the winner of that line.
     * @param node starting node to play
     * @return
     */
    private int simulateRandomPlayout(Node node) {
        Node tempNode = new Node(node);
       while(!Board.gameOver(tempNode.getState().getBoard())){
           if(tempNode.getChildren().size() == 0) //node has not been generated before
               tempNode.generateChildren(); //generate possible children of node

           //pick random child and play move
           tempNode = tempNode.getRandomChildNode();

       }
       int boardStatus = Game.getWinner(tempNode.getState().getBoard().getBlackPieces(), tempNode.getState().getBoard().getWhitePieces(), true);
       backPropagate(tempNode, boardStatus);


        /*State tempState = tempNode.getState();
        int boardStatus = 0;
        if(Board.gameOver(tempState.getBoard())) //if game over set status to winning side
            boardStatus = Game.getWinner(tempState.getBoard().getBlackPieces(), tempState.getBoard().getWhitePieces(), true);
        while(!Board.gameOver(tempState.getBoard())){
            tempState = tempState.randomPlay();
            if(Board.gameOver(tempState.getBoard())) //if game over set status to winning side
                boardStatus = Game.getWinner(tempState.getBoard().getBlackPieces(), tempState.getBoard().getWhitePieces(), true);

        }*/
        //tempState.getBoard().printBoard();
        return boardStatus; //return winner of game
    }

    /**
     * This function takes in a node and generates all possible states from that node.
     * @param promisingNode node to expand
     */
    private void expandNode(Node promisingNode) {
        ArrayList<State> possibleStates = promisingNode.getState().getPossibleStates();
        Iterator<State> it = possibleStates.iterator();
        while(it.hasNext()) {
            State state = it.next();
            Node newNode = new Node(state);
            newNode.setParent(promisingNode);
            promisingNode.getChildren().add(newNode);
        }
    }

    /**
     * This function checks the children of root node in order to find the best node to expand.
     * @param rootNode
     * @return
     */
    private Node selectPromisingNode(Node rootNode){
        Node node = rootNode;
        while(node.getChildren().size() != 0){
            node = UCT.findBestNodeWithUCT(node);
        }
        return node;
    }
}
