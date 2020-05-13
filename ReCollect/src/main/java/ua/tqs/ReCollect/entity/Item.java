package ua.tqs.ReCollect.entity;


import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    public String nome;
    public double preco;
    public int quantidade;
    public String descricao;
    public String imageURL;
    public boolean vendido;
    public ArrayList<Comment> commentsList = new ArrayList<>();

    @ManyToOne(targetEntity=Category.class, fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    public Category category;

    public Item() {}

    public Item(Long id, String descricao, String imageURL, boolean vendido, Category category) {
        this.id = id;
        this.descricao = descricao;
        this.imageURL = imageURL;
        this.vendido = vendido;
        this.category = category;
        this.commentsList = new ArrayList<>();
    }

    public Item(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.commentsList = new ArrayList<>();
    }

    public Item(String nome, String descricao, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.commentsList = new ArrayList<>();
    }

    public Item(String nome, double preco, int quantidade, Category category) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.category = category;
        this.commentsList = new ArrayList<>();
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isVendido() {
        return vendido;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ArrayList<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(ArrayList commentsList) {
        this.commentsList = commentsList;
    }

    public void addComment(Comment comment) {
        this.commentsList.add(comment);
    }




    @Override
    public String toString() {
        return "Item{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                '}';
    }
}
