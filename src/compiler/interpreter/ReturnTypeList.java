package compiler.interpreter;

import java.util.LinkedList;
import java.util.ListIterator;

public class ReturnTypeList {

    private LinkedList<ReturnType> returnList;

    public ReturnTypeList() {
        returnList = new LinkedList<>();
    }

    public ReturnTypeList(ReturnType type){
        returnList = new LinkedList<>();
        returnList.add(type);
    }

    public int size(){
        return returnList.size();
    }

    public ReturnType getFirst(){ return returnList.peek();}

    public void concat(ReturnType list) {
        returnList.add(list);
    }

    public void concat(ReturnTypeList list){
        ListIterator<ReturnType> listIterator = list.returnList.listIterator();
        while (listIterator.hasNext()) {
            returnList.add(listIterator.next());
        }
    }

    public ListIterator<ReturnType> listIterator(){
        return returnList.listIterator();
    }

    @Override
    public String toString() {
        //StringBuilder stringBuilder = new S
        return returnList.toString();
    }
}
