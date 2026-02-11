package repository;
import java.util.List;
import java.util.Optional;

import entity.Livro;

public interface LivroRepository {
    Livro salvar(Livro livro);
    List<Livro> listarTodos();
    Optional<Livro> buscarPorId(int id);
    Optional<Livro> buscarPorIsbn(String isbn);
    boolean atualizar(Livro livro);
    boolean deletar(int id);
    int contar();
}
