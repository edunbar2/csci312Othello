package MonteCarloAI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Node {

    private State state;
    private Node parent;
    private ArrayList<Node> children; //possible moves from this position.

    public Node(State state){

        this.state = state;
        children = new ArrayList<Node>();
       /* //initialize children array
        ArrayList<State> childStates = this.state.getPossibleStates();
        for(int i = 0; i < childStates.size(); i++){
            Node node = new Node(childStates.get(i), this);
        }*/
    }

    public Node(State state, Node parent){
        this.parent = parent;
        this.state = state;
        this.children = new ArrayList<Node>();
    }

    public Node(Node node){

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

    public void generateChildren(){
        ArrayList<State> states = this.state.getPossibleStates();
        Iterator<State> it = states.iterator();
        while(it.hasNext()){
            Node childNode = new Node(it.next(), this);
            this.children.add(childNode);
        }
    }

    public Node getRandomChildNode() {
            Random ran = new Random();
            int index = ran.nextInt(children.size());
            return this.children.get(index);
    }

    public Node getMaxChild() {
        //find child with highest winscore
        Node maxChild = null;
        if(this.children.size() == 0){
            this.generateChildren();
        }
        for (int i = 0; i < this.children.size(); i++) {
            Node tempNode = this.children.get(i);
            if(maxChild == null)
                maxChild = this.children.get(i);
            else if(maxChild.getState().getWinScore() < tempNode.getState().getWinScore()){
                maxChild = tempNode;
            }
        }
        return maxChild;
    }
}
