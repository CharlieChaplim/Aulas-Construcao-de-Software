package service;

import entity.Pedido;
import entity.StatusPedido;
import repository.PedidoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository não pode ser null");
    }


    public Pedido cadastrarPedido(String cliente, String descricao, double valor) {
        validarCliente(cliente);
        validarDescricao(descricao);
        validarValor(valor);

        int novoId = gerarNovoId();
        Pedido pedido = new Pedido(
                novoId,
                cliente.trim(),
                descricao.trim(),
                valor,
                StatusPedido.CRIADO,
                LocalDateTime.now()
        );

        repository.salvar(pedido);
        return pedido;
    }

    public List<Pedido> listarPedidos() {
        return repository.listarTodos();
    }

    public Pedido buscarPedidoPorId(int id) {
        validarId(id);
        return repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido ID " + id + " não encontrado."));
    }

    public Pedido atualizarPedido(int id, String novoCliente, String novaDescricao, double novoValor) {
        validarId(id);
        validarCliente(novoCliente);
        validarDescricao(novaDescricao);
        validarValor(novoValor);

        Pedido existente = buscarPedidoPorId(id);

        if (existente.getStatus() == StatusPedido.CANCELADO) {
            throw new IllegalStateException("Não é permitido atualizar um pedido CANCELADO (ID " + id + ").");
        }

        Pedido atualizado = new Pedido(
                existente.getId(),
                novoCliente.trim(),
                novaDescricao.trim(),
                novoValor,
                existente.getStatus(),
                existente.getDataCriacao()
        );

        boolean ok = repository.atualizar(atualizado);
        if (!ok) {
            throw new IllegalStateException("Falha ao atualizar: pedido ID " + id + " não existe no repositório.");
        }

        return atualizado;
    }

    public Pedido marcarComoPago(int id) {
        validarId(id);

        Pedido existente = buscarPedidoPorId(id);

        if (existente.getStatus() != StatusPedido.CRIADO) {
            throw new IllegalStateException(
                    "Só é possível marcar como PAGO se o pedido estiver CRIADO. Status atual: " + existente.getStatus()
            );
        }

        Pedido atualizado = new Pedido(
                existente.getId(),
                existente.getCliente(),
                existente.getDescricao(),
                existente.getValor(),
                StatusPedido.PAGO,
                existente.getDataCriacao()
        );

        boolean ok = repository.atualizar(atualizado);
        if (!ok) {
            throw new IllegalStateException("Falha ao atualizar status para PAGO no pedido ID " + id + ".");
        }
        return atualizado;
    }

    public Pedido marcarComoEnviado(int id) {
        validarId(id);

        Pedido existente = buscarPedidoPorId(id);

        if (existente.getStatus() != StatusPedido.PAGO) {
            throw new IllegalStateException(
                    "Só é possível marcar como ENVIADO se o pedido estiver PAGO. Status atual: " + existente.getStatus()
            );
        }

        Pedido atualizado = new Pedido(
                existente.getId(),
                existente.getCliente(),
                existente.getDescricao(),
                existente.getValor(),
                StatusPedido.ENVIADO,
                existente.getDataCriacao()
        );

        boolean ok = repository.atualizar(atualizado);
        if (!ok) {
            throw new IllegalStateException("Falha ao atualizar status para ENVIADO no pedido ID " + id + ".");
        }
        return atualizado;
    }

    public Pedido cancelarPedido(int id) {
        validarId(id);

        Pedido existente = buscarPedidoPorId(id);

        if (existente.getStatus() == StatusPedido.ENVIADO) {
            throw new IllegalStateException("Não é permitido cancelar um pedido ENVIADO (ID " + id + ").");
        }

        if (existente.getStatus() == StatusPedido.CANCELADO) {
            throw new IllegalStateException("Pedido já está CANCELADO (ID " + id + ").");
        }

        Pedido atualizado = new Pedido(
                existente.getId(),
                existente.getCliente(),
                existente.getDescricao(),
                existente.getValor(),
                StatusPedido.CANCELADO,
                existente.getDataCriacao()
        );

        boolean ok = repository.atualizar(atualizado);
        if (!ok) {
            throw new IllegalStateException("Falha ao cancelar o pedido ID " + id + ".");
        }
        return atualizado;
    }

    public void removerPedido(int id, boolean confirmacaoUsuario) {
        validarId(id);

        if (!confirmacaoUsuario) {
            throw new IllegalStateException("Remoção cancelada: confirmação do usuário não fornecida.");
        }

        buscarPedidoPorId(id);

        boolean ok = repository.deletar(id);
        if (!ok) {
            throw new IllegalStateException("Falha ao remover: pedido ID " + id + " não existe no repositório.");
        }
    }

    private int gerarNovoId() {
        int maxId = repository.listarTodos().stream()
                .mapToInt(Pedido::getId)
                .max()
                .orElse(0);
        return maxId + 1;
    }

    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido. Deve ser > 0.");
        }
    }

    private void validarCliente(String cliente) {
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("Cliente é obrigatório.");
        }
        if (cliente.trim().length() < 2) {
            throw new IllegalArgumentException("Cliente deve ter pelo menos 2 caracteres.");
        }
    }

    private void validarDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição é obrigatória.");
        }
        if (descricao.trim().length() < 3) {
            throw new IllegalArgumentException("Descrição deve ter pelo menos 3 caracteres.");
        }
    }

    private void validarValor(double valor) {
        if (Double.isNaN(valor) || Double.isInfinite(valor)) {
            throw new IllegalArgumentException("Valor inválido.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que 0.");
        }
        if (valor > 1_000_000_000) {
            throw new IllegalArgumentException("Valor muito alto (suspeito).");
        }
    }
}