package service;

import entity.Funcionario;
import repository.FuncionarioRepository;

import java.util.List;

public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private Integer id = 0;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void salvar(String nome,
                       String cpf,
                       double salario,
                       String telefone,
                       String cargo,
                       String matricula) {

        validarNegocio(telefone, cpf, salario, cargo, matricula);

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        Funcionario funcionario = new Funcionario();
        // NÃO seta id aqui — o repository vai gerar
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setSalario(salario);
        funcionario.setTelefone(telefone);
        funcionario.setCargo(cargo);
        funcionario.setMatricula(Integer.valueOf(matricula));

        funcionarioRepository.salvar(funcionario);
    }

    public List<Funcionario> listar() {
        return funcionarioRepository.listar();
    }

    public Funcionario buscar(Integer id) {
        return funcionarioRepository.buscar(id);
    }

    public void deletar(Integer id) {
        funcionarioRepository.deletar(id);
    }

    public void atualizar(Funcionario f) {
        if (f == null) throw new IllegalArgumentException("Funcionário não pode ser nulo");
        if (f.getId() == null) throw new IllegalArgumentException("ID é obrigatório para atualizar");

        funcionarioRepository.atualizar(f);
    }

    private void validarNegocio(String telefone,
                                String cpf,
                                double salario,
                                String cargo,
                                String matricula) {

        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }
        String cpfNumeros = cpf.replaceAll("\\D", "");
        if (cpfNumeros.length() != 11) {
            throw new IllegalArgumentException("CPF inválido (deve conter 11 dígitos)");
        }

        if (Double.isNaN(salario) || Double.isInfinite(salario)) {
            throw new IllegalArgumentException("Salário inválido");
        }
        if (salario <= 0) {
            throw new IllegalArgumentException("Salário deve ser maior que zero");
        }

        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException("Matrícula é obrigatória");
        }
        if (!matricula.matches("\\d+")) {
            throw new IllegalArgumentException("Matrícula deve conter apenas números");
        }

        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("Telefone é obrigatório");
        }
        String telNumeros = telefone.replaceAll("\\D", "");
        if (telNumeros.length() < 10 || telNumeros.length() > 11) {
            throw new IllegalArgumentException("Telefone inválido (10 ou 11 dígitos com DDD)");
        }

        if (cargo == null || cargo.isBlank()) {
            throw new IllegalArgumentException("Cargo é obrigatório");
        }
        if (cargo.length() < 2) {
            throw new IllegalArgumentException("Cargo inválido");
        }
    }
}
