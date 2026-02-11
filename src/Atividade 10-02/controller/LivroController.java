package controller;

import entity.Livro;
import service.LivroService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LivroController {
    private final LivroService service;
    private final Scanner scanner = new Scanner(System.in);

    public LivroController(LivroService service) {
        this.service = service;
    }

    public void executar() {
        int opcao;
        do {
            System.out.println("=== SISTEMA DE GESTÃO DE LIVROS ===");
            System.out.println("1. Cadastrar Livro");
            System.out.println("2. Listar Todos os Livros");
            System.out.println("3. Buscar Livro por ID");
            System.out.println("4. Atualizar Livro");
            System.out.println("5. Marcar como Indisponível");
            System.out.println("6. Marcar como Disponível");
            System.out.println("7. Remover Livro");
            System.out.println("0. Sair");

            opcao = lerInt("Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1 -> cadastrarLivro();
                    case 2 -> listarLivros();
                    case 3 -> buscarLivroPorId();
                    case 4 -> atualizarLivro();
                    case 5 -> marcarIndisponivel();
                    case 6 -> marcarDisponivel();
                    case 7 -> removerLivro();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

            System.out.println();
        } while (opcao != 0);
    }

    private void cadastrarLivro() {
        String titulo = lerString("Título: ");
        String autor = lerString("Autor: ");
        String isbn = lerString("ISBN: ");
        int ano = lerInt("Ano de publicação: ");

        Livro livro = service.cadastrarLivro(titulo, autor, isbn, ano);
        System.out.println("Livro cadastrado com ID: " + livro.getId());
    }

    private void listarLivros() {
        List<Livro> livros = service.listarLivros();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }

        for (Livro l : livros) {
            imprimirLivro(l);
        }
    }

    private void buscarLivroPorId() {
        int id = lerInt("ID: ");
        Optional<Livro> livro = service.buscarLivroPorId(id);

        if (livro.isEmpty()) {
            System.out.println("Livro não encontrado.");
            return;
        }

        imprimirLivro(livro.get());
    }

    private void atualizarLivro() {
        int id = lerInt("ID: ");

        Optional<Livro> atual = service.buscarLivroPorId(id);
        if (atual.isEmpty()) {
            System.out.println("Livro não encontrado.");
            return;
        }

        String titulo = lerString("Título: ");
        String autor = lerString("Autor: ");
        String isbn = lerString("ISBN: ");
        int ano = lerInt("Ano de publicação: ");

        boolean ok = service.atualizarLivro(id, titulo, autor, isbn, ano);
        System.out.println(ok ? "Livro atualizado com sucesso." : "Erro ao atualizar livro.");
    }

    private void marcarIndisponivel() {
        int id = lerInt("ID: ");
        boolean ok = service.marcarComoIndisponivel(id);
        System.out.println(ok ? "Livro marcado como indisponível." : "Livro não encontrado.");
    }

    private void marcarDisponivel() {
        int id = lerInt("ID: ");
        boolean ok = service.marcarComoDisponivel(id);
        System.out.println(ok ? "Livro marcado como disponível." : "Livro não encontrado.");
    }

    private void removerLivro() {
        int id = lerInt("ID: ");
        boolean ok = service.removerLivro(id);
        System.out.println(ok ? "Livro removido." : "Livro não encontrado.");
    }

    private int lerInt(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Número inválido.");
            }
        }
    }

    private String lerString(String msg) {
        while (true) {
            System.out.print(msg);
            String s = scanner.nextLine().trim();
            if (!s.isEmpty()) {
                return s;
            }
            System.out.println("Valor não pode ser vazio.");
        }
    }

    private void imprimirLivro(Livro l) {
        System.out.println(
                "ID: " + l.getId() +
                        " | Título: " + l.getTitulo() +
                        " | Autor: " + l.getAutor() +
                        " | ISBN: " + l.getIsbn() +
                        " | Ano: " + l.getAnoPublicacao() +
                        " | Disponível: " + (l.isDisponivel() ? "Sim" : "Não")
        );
    }
}

// Menu interativo
// Métodos para cada funcionalidade
// Tratamento de erros
// Métodos auxiliares de leitura

