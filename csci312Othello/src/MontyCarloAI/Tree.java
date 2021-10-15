package MontyCarloAI;

public class Tree {
     // instance variables
    private Node root;

     // constructors
    public Tree(){
        this.root = null;
    }

    public void setRoot(Node root){
        this.root = root;
    }
    public Node getRoot(){
        return this.root;
    }

}
