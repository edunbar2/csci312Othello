package MontyCarloAI;

import edu.unca.csci312.*;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private State state;
    private Node parent;
    private ArrayList<Node> children;

    public Node(State state){
        this.state = state;
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
}
