package MontyCarloAI;
/**
 * @Author Eric Dunbar
 * Inspiration from:
 * https://medium.com/@quasimik/monte-carlo-tree-search-applied-to-letterpress-34f41c86e238
 * https://www.baeldung.com/java-monte-carlo-tree-search
 * https://www.geeksforgeeks.org/ml-monte-carlo-tree-search-mcts/
 */

import edu.unca.csci312.*;
public class MonteCarloSearch {
    private static final int WINSCORE = 10;
    private static final int endTime = 90;
    private int level;
    private int opponent; //1 or -1

    public Board findNextMove(Board currentBoard, int player){
        opponent = player * -1;
        Tree tree = new Tree();
        Node rootNode = tree.getRoot();
        rootNode.getState().setBoard(currentBoard);
        rootNode.getState().setPlayer(opponent);
        double startTime = System.currentTimeMillis()/1000;
        while(((System.currentTimeMillis()/1000) - startTime) < endTime){
            Node promisingNode = selectPromisingNode(rootNode);
            if(!Board.gameOver(promisingNode.getState().getBoard()))
                expandNode(promisingNode);
            Node nodeToExplore = promisingNode;
        }
        return null;
    }

    private Node selectPromisingNode(Node rootNode){
        Node node = rootNode;
        while(node.getChildren().size() != 0){
            node = UCT.findBestNodeWithUCT(node);
        }
        return node;
    }
}
