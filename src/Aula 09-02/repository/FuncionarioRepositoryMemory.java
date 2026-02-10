package repository;

import entity.Funcionario;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioRepositoryMemory implements FuncionarioRepository {

    private final List<Funcionario> funcionarios = new ArrayList<>();
    private int proximoId = 0;

    @Override
    public void salvar(Funcionario f) {
        if (f.getId() == null) {
            f.setId(proximoId++);
            funcionarios.add(f);
        }
    }

    @Override
    public List<Funcionario> listar() {
        return new ArrayList<>(funcionarios);
    }

    @Override
    public Funcionario buscar(Integer id) {
        for (Funcionario f : funcionarios) {
            if (f.getId().equals(id)) return f;
        }
        return null;
    }

    @Override
    public void deletar(Integer id) {
        Funcionario f = buscar(id);
        if (f != null) funcionarios.remove(f);
    }

    @Override
    public void atualizar(Funcionario f) {
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).getId().equals(f.getId())) {
                funcionarios.set(i, f);
                break;
            }
        }
    }
}
