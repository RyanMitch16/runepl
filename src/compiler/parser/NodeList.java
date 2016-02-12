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
     *
     * @param type
     * @param val
     * @param value
     */
    public NodeList(NodeType type, Lexeme val, Node value){
        super(type, val, value);
    }

    /**
     *
     * @param type
     * @param del
     * @param value
     * @param next
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
