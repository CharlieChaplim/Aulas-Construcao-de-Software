package repository;

import entity.Funcionario;
import entity.Livro;

import java.util.*;

public class LivroRepositoryMemory implements LivroRepository {
    private final Map<Integer, Livro> livros = new HashMap<>();
    private int proximoId = 0;

    @Override
    public Livro salvar(Livro livro) {
        if (livro.getId() <= 0) {
            livro.setId(proximoId++);
            livros.put(livro.getId(), livro);
        }
        return livro;
    }

    @Override
    public List<Livro> listarTodos() {
        return List.copyOf(livros.values());
    }

    @Override
    public Optional<Livro> buscarPorId(int id) {
        for (Livro livro : livros.values()) {
            if (livro.getId() == id) {
                return Optional.of(livro);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Livro> buscarPorIsbn(String isbn) {
        for (Livro livro : livros.values()) {
            if (Objects.equals(livro.getIsbn(), isbn)) {
                return Optional.of(livro);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean atualizar(Livro livro) {
        if (livro == null || !livros.containsKey(livro.getId())) {
            return false;
        }

        livros.put(livro.getId(), livro);
        return true;
    }

    @Override
    public boolean deletar(int id) {
        return livros.remove(id) != null;
    }

    @Override
    public int contar() {
        return livros.size();
    }
}
