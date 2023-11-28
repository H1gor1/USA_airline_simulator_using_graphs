# Controle de Voos e Rotas Aéreas

Este repositório contém o projeto desenvolvido pelo aluno Higor Gabriel Lino Silva do curso de Ciência da Computação do Instituto Federal de Minas Gerais - Campus Formiga, como parte da disciplina de Estrutura de Dados II. O trabalho aborda o tema de Controle de Voos e Rotas Aéreas, utilizando grafos para modelar as relações entre aeroportos, rotas e voos.

## Descrição do Projeto

O controle de horários das companhias aéreas é abordado por meio da modelagem de um grafo, onde os vértices representam cidades/aeroportos e as arestas podem representar rotas (não dirigidas) ou voos (dirigidas). Os grafos de rotas e voos compartilham os mesmos vértices, ou seja, os aeroportos. O grafo de rotas possui uma aresta para cada par (origem, destino) com um peso associado, representando a distância entre os aeroportos. O grafo de voos pode ter várias arestas direcionadas para cada par (origem, destino), cada uma com pesos como número do voo, distância, número de paradas e duração.

## Descisões de Projeto

Para o trabalho foram feitas algumas decisões, como a utilização de arquivos json, e a utilização da biblioteca externa "org.json" para a leitura dos mesmos, além disso a representação dos grafos são todas feitas em matrizes de adjacências.

## Funcionalidades da Aplicação

A aplicação desenvolvida inclui as seguintes funcionalidades:

1. **Estrutura de Dados:**
   - Elaboração de uma estrutura de dados que suporta dois grafos: o das rotas e o dos voos.

2. **Leitura de Dados:**
   - Associação de dados a partir de um arquivo sobre voos do território Americano, incluindo informações sobre aeroportos, rotas e voos. As coordenadas são consideradas em dezenas de Km.

3. **Representação Gráfica:**
   - Demonstração gráfica das rotas (ligações entre aeroportos), dos voos, ou ambos.

4. **Relatórios Gerados:**
   - **5.1.** Caminho entre dois aeroportos, apresentado como uma sequência de aeroportos, com base no grafo das rotas.
   - **5.2.** Voos diretos (sem escalas e/ou conexões) que partem de um aeroporto específico, com a lista de destinos.
   - **5.3.** Algoritmo para determinar a viagem com menor custo em termos de distância total a percorrer e tempo de voo, dados uma origem e um destino.
   - **5.4.** Algoritmo para determinar se é possível atingir qualquer aeroporto a partir de um aeroporto qualquer, indicando os aeroportos cruciais.
   - **5.5.** Rota que passa por todos os aeroportos a partir de um aeroporto selecionado, com a verificação de um possível circuito Hamiltoniano.
