package service;

import entity.Livro;
import repository.LivroRepository;

import java.util.List;
import java.util.Optional;

public class LivroService {
    private final LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    private void validarLivro(
            String titulo,
            String autor,
            String isbn,
            int anoPublicacao) {

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título não pode ser vazio");
        }

        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("O autor não pode ser vazio");
        }

        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("O ISBN não pode ser vazio");
        }

        String isbnLimpo = isbn.replace("-", "").trim();

        if (!isbnLimpo.matches("\\d{10}|\\d{13}")) {
            throw new IllegalArgumentException("O ISBN deve conter 10 ou 13 dígitos numéricos");
        }

        int anoAtual = java.time.Year.now().getValue();
        if (anoPublicacao < 1000 || anoPublicacao > anoAtual) {
            throw new IllegalArgumentException("O ano de publicação é inválido");
        }
    }


    public Livro cadastrarLivro(String titulo, String autor, String isbn, int anoPublicacao) {
        validarLivro(titulo, autor, isbn, anoPublicacao);

        Optional<Livro> existente = repository.buscarPorIsbn(isbn);
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Já existe um livro cadastrado com esse ISBN");
        }

        Livro livro = new Livro(titulo, autor, isbn, anoPublicacao);
        return repository.salvar(livro);
    }

    public List<Livro> listarLivros() {
        return repository.listarTodos();
    }

    public Optional<Livro> buscarLivroPorId(int id) {
        if (id <= -1) {
            throw new IllegalArgumentException("Id inválido");
        }
        return repository.buscarPorId(id);
    }

    public boolean atualizarLivro(int id, String titulo, String autor, String isbn, int anoPublicacao) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id inválido");
        }
        validarLivro(titulo, autor, isbn, anoPublicacao);

        Optional<Livro> livroComMesmoIsbn = repository.buscarPorIsbn(isbn);
        if (livroComMesmoIsbn.isPresent() && livroComMesmoIsbn.get().getId() != id) {
            throw new IllegalArgumentException("Já existe outro livro cadastrado com esse ISBN");
        }

        Optional<Livro> atualOpt = repository.buscarPorId(id);
        if (atualOpt.isEmpty()) {
            return false;
        }

        Livro atualizado = new Livro(titulo, autor, isbn, anoPublicacao);
        atualizado.setId(id);
        atualizado.setDisponivel(atualOpt.get().isDisponivel());

        return repository.atualizar(atualizado);
    }

    public boolean removerLivro(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id inválido");
        }
        return repository.deletar(id);
    }

    public boolean marcarComoIndisponivel(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id inválido");
        }

        Optional<Livro> livroOpt = repository.buscarPorId(id);
        if (livroOpt.isEmpty()) {
            return false;
        }

        Livro livro = livroOpt.get();
        if (!livro.isDisponivel()) {
            return true;
        }

        livro.setDisponivel(false);
        return repository.atualizar(livro);
    }

    public boolean marcarComoDisponivel(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id inválido");
        }

        Optional<Livro> livroOpt = repository.buscarPorId(id);
        if (livroOpt.isEmpty()) {
            return false;
        }

        Livro livro = livroOpt.get();
        if (livro.isDisponivel()) {
            return true;
        }

        livro.setDisponivel(true);
        return repository.atualizar(livro);
    }
}

// Métodos com regras de negócio:
// - cadastrarLivro()
// - listarLivros()
// - buscarLivroPorId()
// - atualizarLivro()
// - removerLivro()
// - marcarComoIndisponivel()
// - marcarComoDisponivel()
