package compiler.interpreter;

import java.util.LinkedList;
import java.util.ListIterator;

public class ReturnTypeList {

    private LinkedList<ReturnType> returnList;

    /**
     *
     */
    public ReturnTypeList() {
        returnList = new LinkedList<>();
    }

    /**
     *
     * @param type
     */
    public ReturnTypeList(ReturnType type){
        returnList = new LinkedList<>();
        returnList.add(type);
    }

    /**
     * Get the number of items in the return list.
     * @return the number of returned items
     */
    public int size(){
        return returnList.size();
    }

    /**
     * Get the first element of the list.
     * @return
     */
    public ReturnType getFirst(){ return returnList.peek();}

    /**
     * Add a value to the end of the returned list.
     * @param item the value to add to the list
     */
    public void concat(ReturnType item) {
        returnList.add(item);
    }

    /**
     * Concatenate a list onto the end of this list.
     * @param list the list to add on to the end
     */
    public void concat(ReturnTypeList list){
        ListIterator<ReturnType> listIterator = list.returnList.listIterator();
        while (listIterator.hasNext()) {
            returnList.add(listIterator.next());
        }
    }

    /**
     * Get a iterator to iterate over the returned values
     * @return the list iterator
     */
    public ListIterator<ReturnType> listIterator(){
        return returnList.listIterator();
    }

    /**
     * Get a string representation of the list.
     * @return the string representation of the list
     */
    public String toString() {
        return returnList.toString();
    }
}
