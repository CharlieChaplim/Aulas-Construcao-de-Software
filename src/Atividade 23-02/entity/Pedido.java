package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Pedido {
    private static int contadorId = 1;
    private int id;
    private String cliente;
    private String descricao;
    private double valor;
    private StatusPedido status;
    private LocalDateTime dataCriacao;

    public static int getContadorId() {
        return contadorId;
    }

    public static void setContadorId(int contadorId) {
        Pedido.contadorId = contadorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Pedido(int id, String cliente, String descricao, double valor, StatusPedido status, LocalDateTime dataCriacao) {
        this.id = id;
        this.cliente = cliente;
        this.descricao = descricao;
        this.valor = valor;
        this.status = status;
        this.dataCriacao = dataCriacao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return id == pedido.id && Double.compare(valor, pedido.valor) == 0 && Objects.equals(cliente, pedido.cliente) && Objects.equals(descricao, pedido.descricao) && status == pedido.status && Objects.equals(dataCriacao, pedido.dataCriacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cliente, descricao, valor, status, dataCriacao);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente='" + cliente + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", status=" + status +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
    // Construtor com validações
// Getters e setters com validação
// toString()
// equals() e hashCode()
}