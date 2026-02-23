package controller;

import entity.Pedido;
import service.PedidoService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class PedidoController {

    private final PedidoService service;
    private final Scanner scanner;

    private static final DateTimeFormatter DATA_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public PedidoController(PedidoService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
        this.scanner.useLocale(new Locale("pt", "BR"));
    }

    public void executar() {
        while (true) {
            limparTelaFake();
            mostrarMenu();

            int opcao = lerInt("✦ Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1 -> cadastrar();
                    case 2 -> listarTodos();
                    case 3 -> buscarPorId();
                    case 4 -> atualizar();
                    case 5 -> marcarPago();
                    case 6 -> marcarEnviado();
                    case 7 -> cancelar();
                    case 8 -> remover();
                    case 0 -> {
                        System.out.println("\n✦ Saindo...");
                        return;
                    }
                    default -> {
                        System.out.println("\n✦ Opção inválida. Tente novamente.");
                        pausar();
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("\n✦ Erro de validação: " + e.getMessage());
                pausar();
            } catch (IllegalStateException e) {
                System.out.println("\n✦ Operação não permitida: " + e.getMessage());
                pausar();
            } catch (Exception e) {
                System.out.println("\n✦ Erro inesperado: " + e.getMessage());
                pausar();
            }
        }
    }

    private void cadastrar() {
        System.out.println("\n✦ Cadastro de Pedido");
        System.out.println("✦───────────────────────────────✦");

        String cliente = lerTexto("✦ Cliente: ");
        String descricao = lerTexto("✦ Descrição: ");
        double valor = lerDouble("✦ Valor (ex: 120.50): ");

        Pedido p = service.cadastrarPedido(cliente, descricao, valor);

        System.out.println("\n✦ Pedido cadastrado com sucesso!");
        mostrarDetalhes(p);
        pausar();
    }

    private void listarTodos() {
        System.out.println("\n✦ Lista de Pedidos");
        System.out.println("✦───────────────────────────────✦");

        List<Pedido> pedidos = service.listarPedidos();

        if (pedidos.isEmpty()) {
            System.out.println("✦ Nenhum pedido cadastrado.");
            pausar();
            return;
        }

        imprimirTabela(pedidos);
        pausar();
    }

    private void buscarPorId() {
        System.out.println("\n✦ Buscar Pedido por ID");
        System.out.println("✦───────────────────────────────✦");

        int id = lerInt("✦ ID: ");
        Pedido p = service.buscarPedidoPorId(id);

        System.out.println("\n✦ Pedido encontrado:");
        mostrarDetalhes(p);
        pausar();
    }

    private void atualizar() {
        System.out.println("\n✦ Atualizar Pedido");
        System.out.println("✦───────────────────────────────✦");

        int id = lerInt("✦ ID do pedido: ");

        Pedido atual = service.buscarPedidoPorId(id);
        System.out.println("\n✦ Atual (antes):");
        mostrarDetalhes(atual);

        System.out.println("\n✦ Informe os novos dados:");
        String cliente = lerTexto("✦ Novo Cliente: ");
        String descricao = lerTexto("✦ Nova Descrição: ");
        double valor = lerDouble("✦ Novo Valor: ");

        Pedido atualizado = service.atualizarPedido(id, cliente, descricao, valor);

        System.out.println("\n✦ Pedido atualizado com sucesso!");
        mostrarDetalhes(atualizado);
        pausar();
    }

    private void marcarPago() {
        System.out.println("\n✦ Marcar como PAGO");
        System.out.println("✦───────────────────────────────✦");

        int id = lerInt("✦ ID: ");
        Pedido atualizado = service.marcarComoPago(id);

        System.out.println("\n✦ Pedido marcado como PAGO!");
        mostrarDetalhes(atualizado);
        pausar();
    }

    private void marcarEnviado() {
        System.out.println("\n✦ Marcar como ENVIADO");
        System.out.println("✦───────────────────────────────✦");

        int id = lerInt("✦ ID: ");
        Pedido atualizado = service.marcarComoEnviado(id);

        System.out.println("\n✦ Pedido marcado como ENVIADO!");
        mostrarDetalhes(atualizado);
        pausar();
    }

    private void cancelar() {
        System.out.println("\n✦ Cancelar Pedido");
        System.out.println("✦───────────────────────────────✦");

        int id = lerInt("✦ ID: ");
        Pedido atualizado = service.cancelarPedido(id);

        System.out.println("\n✦ Pedido CANCELADO!");
        mostrarDetalhes(atualizado);
        pausar();
    }

    private void remover() {
        System.out.println("\n✦ Remover Pedido");
        System.out.println("✦───────────────────────────────✦");

        int id = lerInt("✦ ID: ");

        // Mostra antes de remover
        Pedido p = service.buscarPedidoPorId(id);
        System.out.println("\n✦ Você está prestes a remover:");
        mostrarDetalhes(p);

        boolean confirm = lerConfirmacao("✦ Confirmar remoção? (S/N): ");
        service.removerPedido(id, confirm);

        System.out.println("\n✦ Pedido removido com sucesso.");
        pausar();
    }

    private void mostrarMenu() {
        System.out.println("✦══════════════════════════════════════════✦");
        System.out.println("✦   === SISTEMA DE GESTÃO DE PEDIDOS ===   ✦");
        System.out.println("✦══════════════════════════════════════════✦");
        System.out.println("✦ 1. Cadastrar Pedido                       ");
        System.out.println("✦ 2. Listar Todos os Pedidos                ");
        System.out.println("✦ 3. Buscar Pedido por ID                   ");
        System.out.println("✦ 4. Atualizar Pedido                       ");
        System.out.println("✦ 5. Marcar como Pago                       ");
        System.out.println("✦ 6. Marcar como Enviado                    ");
        System.out.println("✦ 7. Cancelar Pedido                        ");
        System.out.println("✦ 8. Remover Pedido                         ");
        System.out.println("✦ 0. Sair                                   ");
        System.out.println("✦══════════════════════════════════════════✦");
    }

    private void imprimirTabela(List<Pedido> pedidos) {
        System.out.println("✦────────────────────────────────────────────────────────────────────────✦");
        System.out.printf("✦ %-4s | %-20s | %-10s | %-10s | %-16s ✦%n",
                "ID", "Cliente", "Valor", "Status", "Data");
        System.out.println("✦────────────────────────────────────────────────────────────────────────✦");

        for (Pedido p : pedidos) {
            String data = (p.getDataCriacao() == null) ? "-" : p.getDataCriacao().format(DATA_FMT);
            System.out.printf("✦ %-4d | %-20s | %10.2f | %-10s | %-16s ✦%n",
                    p.getId(),
                    cortar(p.getCliente(), 20),
                    p.getValor(),
                    p.getStatus(),
                    data
            );
        }

        System.out.println("✦────────────────────────────────────────────────────────────────────────✦");
    }

    private void mostrarDetalhes(Pedido p) {
        String data = (p.getDataCriacao() == null) ? "-" : p.getDataCriacao().format(DATA_FMT);

        System.out.println("✦──────────────────────────────");
        System.out.println("✦ ID: " + p.getId());
        System.out.println("✦ Cliente: " + p.getCliente());
        System.out.println("✦ Descrição: " + p.getDescricao());
        System.out.printf ("✦ Valor: %.2f%n", p.getValor());
        System.out.println("✦ Status: " + p.getStatus());
        System.out.println("✦ Data: " + data);
        System.out.println("✦──────────────────────────────");
    }

    private String lerTexto(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine();
            if (s != null && !s.trim().isEmpty()) return s;
            System.out.println("✦ Campo obrigatório. Tente novamente.");
        }
    }

    private int lerInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("✦ Digite um número inteiro válido.");
            }
        }
    }

    private double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("✦ Digite um número válido (ex: 10.50).");
            }
        }
    }

    private boolean lerConfirmacao(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim().toUpperCase();
            if (s.equals("S") || s.equals("SIM")) return true;
            if (s.equals("N") || s.equals("NAO") || s.equals("NÃO")) return false;
            System.out.println("✦ Responda com S ou N.");
        }
    }

    private void pausar() {
        System.out.print("\n✦ Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    private void limparTelaFake() {
        System.out.print("\n".repeat(25));
    }

    private String cortar(String s, int max) {
        if (s == null) return "";
        s = s.trim();
        if (s.length() <= max) return s;
        return s.substring(0, Math.max(0, max - 1)) + "…";
    }
}