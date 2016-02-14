package compiler.parser;

import compiler.lexer.Lexeme;

public abstract class NodeList extends Node {

    /**
     * The iterator that returns the nodes in the list.
     */
    public static class Iterator {

        //The list of nodes to iterator over
        private Node nodeList;

        /**
         * Instantiate the node iterator.
         * @param nodeList the list of nodes to iterator over
         */
        private Iterator(NodeList nodeList){
            this.nodeList = nodeList;
        }

        /**
         * Check if there are more nodes in the list to be returned.
         * @return  whether there are more nodes to be returned or not
         */
        public boolean hasNext(){
            return (nodeList != null);
        }

        /**
         * Move to the next node to be returned.
         * @return the next node in the list
         */
        public Node next(){

            Node value = nodeList.children[0];
            if (nodeList.children.length == 1) {
                nodeList = null;
            } else {
                nodeList = nodeList.children[1];
            }
            return value;
        }
    }

    /**
     * Instantiate the list with no next node signifying the end of the list.
     * @param type the type of node list
     * @param del the lexeme that represents the position of the list in the file
     * @param value the node the list holds (the car of the cons)
     */
    public NodeList(NodeType type, Lexeme del, Node value){
        super(type, del, value);
    }

    /**
     * Instantiate the list with a next node signifying the list has more nodes to it.
     * @param type the type of node list
     * @param del the lexeme that represents the position of the list in the file
     * @param value the node the list holds (the car of the cons)
     * @param next the next node list in the list (the cdr of the cons)
     */
    public NodeList(NodeType type, Lexeme del, Node value, NodeList next){
        super(type, del, value, next);
    }

    /**
     * Get the iterator that iterates over the contents of the node list.
     * @return  the node list iterator
     */
    public Iterator getIterator(){
        return new Iterator(this);
    }
}
