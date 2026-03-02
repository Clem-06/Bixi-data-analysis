package ca.concordia.model.linkedList;

import java.util.Iterator;

public class List<T> implements LinkedList<T>, Iterable<T> {

    private Node<T> head;
    int n;
    boolean isEmpty;

    public List() {
        head = null;
        n = 0;
        isEmpty = true;
    }

    public void push(T item) {
        Node newNode = new Node();
        newNode.element = item;
        newNode.next = head;
        this.head = newNode;
        n++;
    }

    public int sizeOf() {
        return n;
    }

    public boolean isEmpty() {
        return (head == null);
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("EMPTY LINKED LIST");
        } else {
            Node current = head;
            while (current.next != null) {
                System.out.print(current.element + " -> ");
                current = current.next;
            }
            System.out.println(current.element);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T value = current.element;
                current = current.next;
                return value;
            }
        };
    }

    public void append(List<T> other) {
        if (this.head == null) {
            this.head = other.head;
            this.n = other.n;
            return;
        }
        Node<T> current = head;

        while (current.next != null) {
            current = current.next;
        }

        current.next = other.head; //magic linkin or smt
        this.n += other.n;
    }

    private class Node<T> {
        T element;
        Node<T> next;
    }

}
