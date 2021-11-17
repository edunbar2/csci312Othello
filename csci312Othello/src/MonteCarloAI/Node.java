package MonteCarloAI;

import java.util.ArrayList;
import java.util.Random;

public class Node {

    private Node maxChild;
    private State state;
    private Node parent;
    private ArrayList<Node> children; //possible moves from this position.

    public Node(State state){

        this.state = state;
        children = new ArrayList<>();
       /* //initialize children array
        ArrayList<State> childStates = this.state.getPossibleStates();
        for(int i = 0; i < childStates.size(); i++){
            Node node = new Node(childStates.get(i), this);
        }*/
    }

    public Node(State state, Node parent){
        this.parent = parent;
        this.state = state;
    }

    public Node(Node node){
        this.maxChild = node.maxChild;
        this.state = node.state;
        this.parent = node.parent;
        this.children = node.children;
    }

     // getters and setters
    public void setGameState(State state){
        this.state = state;
    }
    public State getState(){
        return this.state;
    }

    public void setParent(Node parent){
        this.parent = parent;
    }
    public Node getParent(){
        return this.parent;
    }

    public void setChildren(ArrayList<Node> children){
        this.children = children;
    }
    public ArrayList<Node> getChildren(){
        return this.children;
    }

    public Node getRandomChildNode() {
            Random ran = new Random();
            int index = ran.nextInt(children.size());
            return this.children.get(index);
    }

    public Node getMaxChild() {
        return this.maxChild;
    }
    public void setMaxChild(Node maxChild){this.maxChild = maxChild;}
}
