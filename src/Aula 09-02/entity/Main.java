package entity;

import controller.FuncionarioController;
import entity.Funcionario;
import repository.FuncionarioRepository;
import repository.FuncionarioRepositoryMemory;
import service.FuncionarioService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner ler = new Scanner(System.in);

    public static void main(String[] args) {
        FuncionarioRepository funcionarioRepository = new FuncionarioRepositoryMemory();
        FuncionarioService funcionarioService = new FuncionarioService(funcionarioRepository);
        FuncionarioController funcionarioController = new FuncionarioController(funcionarioService);

        while (true) {
            System.out.println("\n=== MENU FUNCIONÁRIO ===");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Buscar por ID");
            System.out.println("4 - Atualizar");
            System.out.println("5 - Deletar");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            String opcao = ler.nextLine().trim();

            try {
                switch (opcao) {
                    case "1" -> {
                        System.out.print("Nome: ");
                        String nome = ler.nextLine();

                        System.out.print("CPF: ");
                        String cpf = ler.nextLine();

                        double salario = lerDouble("Salário: ");

                        System.out.print("Telefone: ");
                        String telefone = ler.nextLine();

                        System.out.print("Cargo: ");
                        String cargo = ler.nextLine();

                        System.out.print("Matrícula (somente números): ");
                        String matricula = ler.nextLine();

                        funcionarioController.salvar(nome, cpf, salario, telefone, cargo, matricula);
                        System.out.println("Funcionário cadastrado com sucesso!");
                    }

                    case "2" -> {
                        List<Funcionario> lista = funcionarioService.listar();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhum funcionário cadastrado.");
                        } else {
                            System.out.println("\n--- Funcionários ---");
                            for (Funcionario f : lista) {
                                System.out.println(f);
                            }
                        }
                    }

                    case "3" -> {
                        int id = lerInt("ID: ");
                        Funcionario f = funcionarioService.buscar(id);
                        if (f == null) {
                            System.out.println("Funcionário não encontrado.");
                        } else {
                            System.out.println("Encontrado: " + f);
                        }
                    }

                    case "4" -> {
                        int id = lerInt("ID do funcionário a atualizar: ");
                        Funcionario atual = funcionarioService.buscar(id);

                        if (atual == null) {
                            System.out.println("Funcionário não encontrado.");
                            break;
                        }

                        System.out.println("Deixe em branco para manter o valor atual.");

                        System.out.print("Nome (" + atual.getNome() + "): ");
                        String nome = ler.nextLine();
                        if (nome.isBlank()) nome = atual.getNome();

                        System.out.print("CPF (" + atual.getCpf() + "): ");
                        String cpf = ler.nextLine();
                        if (cpf.isBlank()) cpf = atual.getCpf();

                        System.out.print("Salário (" + atual.getSalario() + "): ");
                        String salarioStr = ler.nextLine().trim();
                        double salario = salarioStr.isBlank() ? atual.getSalario() : Double.parseDouble(salarioStr);

                        System.out.print("Telefone (" + atual.getTelefone() + "): ");
                        String telefone = ler.nextLine();
                        if (telefone.isBlank()) telefone = atual.getTelefone();

                        System.out.print("Cargo (" + atual.getCargo() + "): ");
                        String cargo = ler.nextLine();
                        if (cargo.isBlank()) cargo = atual.getCargo();

                        System.out.print("Matrícula (" + atual.getMatricula() + "): ");
                        String matriculaStr = ler.nextLine().trim();
                        Integer matricula = matriculaStr.isBlank() ? atual.getMatricula() : Integer.valueOf(matriculaStr);

                        Funcionario novo = new Funcionario();
                        novo.setId(id);
                        novo.setNome(nome);
                        novo.setCpf(cpf);
                        novo.setSalario(salario);
                        novo.setTelefone(telefone);
                        novo.setCargo(cargo);
                        novo.setMatricula(matricula);

                        funcionarioService.atualizar(novo);
                        System.out.println("Funcionário atualizado!");
                    }

                    case "5" -> {
                        int id = lerInt("ID do funcionário a deletar: ");
                        Funcionario f = funcionarioService.buscar(id);
                        if (f == null) {
                            System.out.println("Funcionário não encontrado.");
                        } else {
                            funcionarioService.deletar(id);
                            System.out.println("Funcionário deletado!");
                        }
                    }

                    case "0" -> {
                        System.out.println("Saindo...");
                        return;
                    }

                    default -> System.out.println("Opção inválida!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }

    private static int lerInt(String rotulo) {
        while (true) {
            System.out.print(rotulo);
            String s = ler.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static double lerDouble(String rotulo) {
        while (true) {
            System.out.print(rotulo);
            String s = ler.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido (ex: 1500.50).");
            }
        }
    }
}
