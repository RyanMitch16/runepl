package compiler.parser;

import compiler.lexer.Lexeme;

public abstract class NodeList extends Node {

    public static class Iterator {

        private Node nodeList;

        private Iterator(NodeList nodeList){
            this.nodeList = nodeList;
        }

        public boolean hasNext(){
            return (nodeList != null);
        }

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

    public NodeList(NodeType type, Lexeme val, Node value){
        super(type, val, value);
    }

    public NodeList(NodeType type, Lexeme del, Node value, NodeList next){
        super(type, del, value, next);
    }

    public Iterator getIterator(){
        return new Iterator(this);
    }
}
