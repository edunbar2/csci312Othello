package MonteCarloAI;

import edu.unca.csci312.Board;

public class Tree {
     // instance variables
    private Node root;

     // constructors
    public Tree(Board gameBoard, int player){
        State state = new State(null, gameBoard,player);
        this.root = new Node(state);
    }

    public void setRoot(Node root){
        this.root = root;
    }
    public Node getRoot(){
        return this.root;
    }

}
