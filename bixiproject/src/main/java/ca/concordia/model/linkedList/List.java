package ca.concordia.model.linkedList;

public class List<T> implements LinkedList<T> {

    private Node<T> head;
    int n;
    boolean isEmpty;

    public List(){
        head = null;
        n = 0;
        isEmpty = true;
    }

    public void push(T item){
        Node newNode = new Node();
        newNode.element = item;
        newNode.next = head;
        this.head = newNode;
        n++;
    }
    public int sizeOf(){
        return n;
    }
    public boolean isEmpty(){
        return (head ==null);
    }

    public void display(){
        if (isEmpty()){
            System.out.println("EMPTY LINKED LIST");
        }else {
            Node current = head;
            while (current.next!=null){
                System.out.print (current.element+ " -> ");
                current = current.next;
            }
            System.out.println(current.element);
        }
    }

    private class Node<T>{
        T element;
        Node<T> next;
    }

}
