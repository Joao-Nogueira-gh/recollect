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
    private String categoria;

    private Long owner;

    private Long seller;

    // usei Set porque nao podem ser repetidos e por que com List<> não
    // dá para usar FetchType.EAGER em mais do que uma
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Comment> commentsList;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> imagens;

    // TODO
    //private Set<Comentario> comentarios;

    public Item() {
        this.imagens = new ArrayList<>();
        this.commentsList = new HashSet<>();
        this.seller = null;
    }


    public Item(String nome, double preco, int quantidade, String descricao, String categoria) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.imagens = new ArrayList<>();
        this.categoria = categoria;
        this.commentsList = new HashSet<>();
    }

    public Item(String nome, String descricao, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.commentsList = new HashSet<>();
        this.imagens = new ArrayList<>();
    }


    @Deprecated
    public Item(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.commentsList = new HashSet<>();
        this.imagens = new ArrayList<>();
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

    public Set<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(Set commentsList) {
        this.commentsList = commentsList;
    }

    public void addComment(Comment comment) {
        this.commentsList.add(comment);
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public Long getSeller() {
        return seller;
    }

    public void setSeller(Long seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", categoria='" + categoria + '\'' +
                ", owner=" + owner +
                ", seller=" + seller +
                ", commentsList=" + commentsList +
                ", imagens=" + imagens +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, preco, quantidade, descricao, categoria, commentsList, imagens);
    }
}
