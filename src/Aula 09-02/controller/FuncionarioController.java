package controller;

import service.FuncionarioService;

public class FuncionarioController {

    private FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    public void setFuncionarioService(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    public void salvar(String nome,
                       String cpf,
                       double salario,
                       String telefone,
                       String cargo,
                       String matricula) {

        if (funcionarioService == null) {
            throw new IllegalStateException("FuncionarioService não foi configurado no Controller");
        }

        funcionarioService.salvar(nome, cpf, salario, telefone, cargo, matricula);
    }
}
