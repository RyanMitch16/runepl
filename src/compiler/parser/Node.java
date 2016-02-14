package compiler.parser;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnTypeList;
import compiler.lexer.Lexeme;

import java.util.ArrayList;

public abstract class Node {

    //The parent of the token node in the tree
    private Node parent;

    //The children of the token node in the tree
    public Node children[];

    //The lexeme read from the file
    public Lexeme lexeme;

    //The type of node
    public NodeType type;

    /**
     * Initiate a node that holds the syntactical information of the code
     */
    public Node(NodeType type, Lexeme lexeme, Node... nodes){
        this.type = type;
        this.lexeme = lexeme;
        parent = null;

        //Set the children and parents of the nodes
        children = nodes;
        for (Node node : nodes){
            node.parent = this;
        }
    }

    /**
     * Get the parent of the node
     * @return the parent of the node
     */
    public Node getParent(){
        return parent;
    }

    /**
     * Check if the node is a left child
     * @return  if the node is a left child
     */
    public boolean hasParent(){
        return (parent != null);
    }

    /**
     * Get the root of the tree the node belongs to
     * @return the root node of the tree
     */
    public Node getRoot(){

        Node node = this;
        while (node.getParent() != null){
            node = node.getParent();
        }

        return node;
    }

    /**
     * Get the number of children elements underneath this node along with this element
     * @return  the sie of the node
     */
    private int getSize(){

        int sum = 1;
        for (Node node : children){
            sum += node.getSize();
        }

        return sum;

    }

    /**
     * Get an array of strings representing the tree from this node
     * @return the array representation of the tree underneath this node
     */
    private ArrayList<String> getString(){

        ArrayList<String> out = new ArrayList<>();
        String text = type + ((lexeme.text!=null)?"("+lexeme.text+")":"");
        String whiteSpace = text.replaceAll("."," ")+"  ";

        out.add("|"+ text);

        for (int i=children.length-1;i>=0;i--){
            ArrayList<String> contents = children[i].getString();
            for (String str : contents) {
                out.add(whiteSpace + str);
            }
        }

        return out;
    }

    /**
     * Get a string representation of the tree from the node
     * @return a string representation of the tree from the node
     */
    @Override
    public String toString(){

        ArrayList<String> contents = getString();
        String out = "";
        for (String str : contents){
            out += str+"\n";
        }

        return out;
    }


    public abstract ReturnTypeList eval(Environment env) throws RunTimeException;
}

