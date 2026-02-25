package ca.concordia.model.linkedList;

public interface LinkedList<T> {
    void push (T item);
    boolean isEmpty();
    int sizeOf();

    void display();
}
