package br.com.pizzaria.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoria;

    public Produto() {
    }

    public Produto(int id, String nome, String descricao, BigDecimal preco, String categoria) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode ser vazio.");
        }
        if (preco == null || preco.signum() < 0) {
            throw new IllegalArgumentException("O preço deve ser maior ou igual a zero.");
        }
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
