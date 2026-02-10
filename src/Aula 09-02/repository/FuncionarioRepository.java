package repository;

import entity.Funcionario;

import java.util.List;

public interface FuncionarioRepository {

    void salvar(Funcionario f);
    List<Funcionario> listar();
    Funcionario buscar(Integer id);
    void deletar(Integer id);
    void atualizar(Funcionario f);

}
