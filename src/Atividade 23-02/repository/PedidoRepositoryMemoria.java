package repository;

import entity.Pedido;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PedidoRepositoryMemoria implements PedidoRepository {

    private final Map<Integer, Pedido> pedidos = new HashMap<>();
    private int proximoId = 1;

    @Override
    public Pedido salvar(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não pode ser null.");
        }

        if (pedido.getId() <= 0) {
            pedido.setId(proximoId++);
        } else {
            proximoId = Math.max(proximoId, pedido.getId() + 1);
        }

        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }

    @Override
    public List<Pedido> listarTodos() {
        List<Pedido> lista = new ArrayList<>(pedidos.values());
        lista.sort(Comparator.comparingInt(Pedido::getId));
        return lista;
    }

    @Override
    public Optional<Pedido> buscarPorId(int id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    @Override
    public boolean atualizar(Pedido pedido) {
        if (pedido == null) return false;

        int id = pedido.getId();
        if (id <= 0) return false;

        if (!pedidos.containsKey(id)) {
            return false;
        }

        pedidos.put(id, pedido);
        return true;
    }

    @Override
    public boolean deletar(int id) {
        return pedidos.remove(id) != null;
    }

    @Override
    public int contar() {
        return pedidos.size();
    }
}