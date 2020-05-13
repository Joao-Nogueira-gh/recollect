package ua.tqs.ReCollect.entity;


import javax.persistence.*;
import java.util.*;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String nome;
    private double preco;
    private int quantidade;
    private String descricao;
    private boolean vendido;
    private String categoria;

    @ElementCollection
    private List<String> imagens;

    // TODO
    //private Set<Comentario> comentarios;

    public Item() {
        this.imagens = new ArrayList<>();
        this.vendido = false;
    }


    public Item(String nome, double preco, int quantidade, String descricao, String categoria) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.imagens = new ArrayList<>();
        this.categoria = categoria;
        this.vendido = false;
    }


    @Deprecated
    public Item(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;

    }

    public void addImage(String url){
        this.imagens.add(url);
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public boolean isVendido() {
        return vendido;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<String> getImagens() {
        return imagens;
    }

    public void setImagens(List<String> imagens) {
        this.imagens = imagens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.preco, preco) == 0 &&
                quantidade == item.quantidade &&
                vendido == item.vendido &&
                Objects.equals(id, item.id) &&
                Objects.equals(nome, item.nome) &&
                Objects.equals(descricao, item.descricao) &&
                Objects.equals(categoria, item.categoria) &&
                Objects.equals(imagens, item.imagens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, preco, quantidade, descricao, vendido, categoria, imagens);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", vendido=" + vendido +
                ", categoria='" + categoria + '\'' +
                ", imagens=" + imagens +
                '}';
    }
}
