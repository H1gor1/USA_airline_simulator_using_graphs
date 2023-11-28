package edu.ifmg.structures;

public class ListaEncadeada<T> {
    // Nó inicial da lista encadeada
    Node<T> head;

    // Construtor da classe
    public ListaEncadeada() {
        // Inicializa a cabeça da lista como nula
        head = null;
    }

    // Método para adicionar um elemento à lista
    public void add(T data) {
        // Cria um novo nó com os dados fornecidos
        Node<T> newNode = new Node<>(data);
        // Se a lista estiver vazia, o novo nó se torna a cabeça da lista
        if (head == null) {
            head = newNode;
        } else {
            // Caso contrário, percorre a lista até o último nó
            Node<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            // Adiciona o novo nó no final da lista
            temp.next = newNode;
        }
    }

    // Método para imprimir todos os elementos da lista
    public void printList() {
        // Começa na cabeça da lista
        Node<T> temp = head;
        // Percorre a lista e imprime cada elemento
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }

    // Método para remover e retornar o primeiro elemento da lista
    public T poll() {
        // Se a lista estiver vazia, retorna nulo
        if (head == null) {
            return null;
        }
        // Caso contrário, remove a cabeça da lista e a substitui pelo próximo nó
        T data = head.data;
        head = head.next;
        // Retorna os dados do nó removido
        return data;
    }

    // Método para obter o tamanho da lista
    public int size() {
        int size = 0;
        Node<T> temp = head;
        // Percorre a lista e incrementa o tamanho para cada nó
        while (temp != null) {
            size++;
            temp = temp.next;
        }
        return size;
    }

    // Método para verificar se a lista está vazia
    public boolean isEmpty() {
        return head == null;
    }
}