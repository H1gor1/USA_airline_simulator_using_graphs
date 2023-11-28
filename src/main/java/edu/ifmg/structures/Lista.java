package edu.ifmg.structures;
import java.util.Arrays;
public class Lista<T> {
    // Capacidade inicial do array
    private static final int INITIAL_CAPACITY = 10;
    // Array para armazenar os elementos da lista
    private Object[] elements;
    // Variável para armazenar o tamanho atual da lista
    private int size;

    // Construtor da classe
    public Lista(){
        // Inicializa o array com a capacidade inicial
        elements = new Object[INITIAL_CAPACITY];
        // Inicializa o tamanho da lista como 0
        size = 0;
    }

    // Método para adicionar um elemento à lista
    public void add(T element) {
        // Se o tamanho da lista é igual à capacidade do array, aumenta a capacidade
        if (size == elements.length) {
            increaseCapacity();
        }
        // Adiciona o elemento no final da lista
        elements[size] = element;
        // Incrementa o tamanho da lista
        size++;
    }

    // Método para obter um elemento da lista pelo índice
    public T get(int index) {
        // Se o índice é inválido, lança uma exceção
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        // Retorna o elemento no índice especificado
        @SuppressWarnings("unchecked")
        T element = (T) elements[index];
        return element;
    }

    // Método para remover um elemento da lista pelo índice
    public void remove(int index) {
        // Se o índice é inválido, lança uma exceção
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        // Move todos os elementos após o índice para uma posição anterior
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        // Decrementa o tamanho da lista
        size--;
    }

    // Método para obter o tamanho da lista
    public int size() {
        return size;
    }

    // Método para verificar se a lista contém um elemento específico
    public boolean contains(T element) {
        // Percorre todos os elementos da lista
        for (int i = 0; i < size; i++) {
            // Se o elemento atual é igual ao elemento especificado, retorna true
            if (elements[i].equals(element)) {
                return true;
            }
        }
        // Se o elemento não foi encontrado, retorna false
        return false;
    }

    // Método para aumentar a capacidade do array
    private void increaseCapacity() {
        // Dobra a capacidade atual do array
        int newCapacity = elements.length * 2;
        // Cria um novo array com a nova capacidade e copia os elementos do array antigo para o novo array
        elements = Arrays.copyOf(elements, newCapacity);
    }

    // Método para verificar se a lista está vazia
    public boolean isEmpty() {
        return size() == 0;
    }
}
