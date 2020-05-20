package ua.tqs.ReCollect.utils;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class ItemForm {

    @NotNull
    @Size(min=2, max=80)
    private String nome;

    @NotNull
    private String categoria;

    @NotNull
    private String descricao;

    @NotNull
    @PositiveOrZero
    private double preco;

    @NotNull
    @Positive
    private int quantidade;



    public ItemForm() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


    @Override
    public String toString() {
        return "ItemForm{" +
                "nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                '}';
    }
}


