package edu.ifmg.structures;

public class ListaEncadeada<T> {
    Node<T> head;

    public ListaEncadeada() {
        head = null;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public void printList() {
        Node<T> temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }

    public T poll() {
        if (head == null) {
            return null;
        }
        T data = head.data;
        head = head.next;
        return data;
    }

    public int size() {
        int size = 0;
        Node<T> temp = head;
        while (temp != null) {
            size++;
            temp = temp.next;
        }
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }
}


