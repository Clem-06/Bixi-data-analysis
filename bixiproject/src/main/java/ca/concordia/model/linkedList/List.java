package ca.concordia.model.linkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class List<T> implements LinkedList<T>, Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    int n;
    boolean isEmpty;

    public List() {
        head = null;
        n = 0;
        isEmpty = true;
    }

    public void push(T item) {
        Node<T> newNode = new Node<>();
        newNode.element = item;
        newNode.next = head;
        head = newNode;
        if (tail == null)
            tail = newNode;
        n++;
    }

    public void pushBack(T item) {
        Node<T> newNode = new Node<>();
        newNode.element = item;
        newNode.next = null;

        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

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
                if (current == null)
                    throw new NoSuchElementException();

                T value = current.element;
                current = current.next;
                return value;
            }
        };
    }

    public void append(List<T> other) {
        if (other == null || other.head == null)
            return;

        if (this.head == null) {
            this.head = other.head;
            this.tail = other.tail;
            this.n = other.n;
            return;
        }

        this.tail.next = other.head;
        this.tail = other.tail;
        this.n += other.n;
    }

    private class Node<T> {
        T element;
        Node<T> next;
    }

}
