package edu.ifmg;
import java.util.Arrays;
public class Lista<T> {
    private static final int INITIAL_CAPACITY = 10;
    private Object[] elements;
    private int size;

    public Lista(){
        elements = new Object[INITIAL_CAPACITY];
        size = 0;
    }

    public void add(T element) {
        if (size == elements.length) {
            increaseCapacity();
        }
        elements[size] = element;
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        @SuppressWarnings("unchecked")
        T element = (T) elements[index];
        return element;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--;
    }

    public int size() {
        return size;
    }

    private void increaseCapacity() {
        int newCapacity = elements.length * 2;
        elements = Arrays.copyOf(elements, newCapacity);
    }
}
