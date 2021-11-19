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
    private static final double endTime = 600.0 / 70.0;
    private int level;
    private int opponent; //1 or -1

    public MonteCarloSearch(){
        this.opponent = 0;
        this.level = 0;
    }

    /**
     * This function powers the MCTS algorithm, following the selection, expansion
     * simulation, and backpropagation pattern.
     * @param currentBoard
     * @param player
     * @return
     */
    public Move findNextMove(Board currentBoard, int player){
        opponent = player * -1;
        Tree tree = new Tree(currentBoard, player);
        Node rootNode = tree.getRoot();
        rootNode.getState().setBoard(currentBoard);
        rootNode.getState().setPlayer(player);
        double startTime = System.currentTimeMillis()/1000.0;
        while((((System.currentTimeMillis()/1000.0) - startTime)) < endTime){
           // System.out.println("C Time: " + (System.currentTimeMillis()/1000 - startTime));
            Node promisingNode = selectPromisingNode(rootNode);
            if(!Board.gameOver(promisingNode.getState().getBoard()))
                expandNode(promisingNode);
            Node nodeToExplore = promisingNode;
            if(promisingNode.getChildren().size() > 0){
                nodeToExplore = promisingNode.getRandomChildNode();
            }
            int playoutResult = simulateRandomPlayout(nodeToExplore);
            backPropagate(nodeToExplore, playoutResult);
        }

        Node winnderNode = rootNode.getMaxChild();
        tree.setRoot(winnderNode);
        return winnderNode.getState().getMove_to_state();
    }

    private void backPropagate(Node nodeToExplore, int playoutResult) {
        Node tempNode = nodeToExplore;
        while(tempNode != null){
            tempNode.getState().incrementVisitCount();
            if(tempNode.getState().getPlayer() == playoutResult){
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
        State tempState = tempNode.getState();
        int boardStatus = 0;
        if(Board.gameOver(tempState.getBoard())) //if game over set status to winning side
            boardStatus = Game.getWinner(tempState.getBoard().getBlackPieces(), tempState.getBoard().getWhitePieces(), true);
        while(!Board.gameOver(tempState.getBoard())){
            tempState.setPlayer(tempState.getPlayer() * -1);
            tempState.randomPlay();
            if(Board.gameOver(tempState.getBoard())) //if game over set status to winning side
                boardStatus = Game.getWinner(tempState.getBoard().getBlackPieces(), tempState.getBoard().getWhitePieces(), true);

        }
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
            newNode.getState().setPlayer(newNode.getState().getPlayer() * -1);
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
