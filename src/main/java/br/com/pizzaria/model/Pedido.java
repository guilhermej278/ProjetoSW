package br.com.pizzaria.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Usuario usuario;
    private String endereco;
    private String tipoPedido;
    private BigDecimal total;
    private String status;
    private LocalDateTime criadoEm;

    public Pedido() {
    }

    public Pedido(Usuario usuario, String endereco, String tipoPedido, BigDecimal total) {
        this.usuario = usuario;
        this.endereco = endereco;
        this.tipoPedido = tipoPedido;
        this.total = total;
        this.status = "RECEBIDO";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getTipoPedido() { return tipoPedido; }
    public void setTipoPedido(String tipoPedido) { this.tipoPedido = tipoPedido; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}
