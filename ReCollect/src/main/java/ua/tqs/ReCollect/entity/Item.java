package ua.tqs.ReCollect.entity;


import javax.persistence.*;

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

    @ManyToOne(targetEntity=Category.class, fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    public Category category;

    public Item() {}

    public Item(Long id, String descricao, String imageURL, boolean vendido, Category category) {
        this.id = id;
        this.descricao = descricao;
        this.imageURL = imageURL;
        this.vendido = vendido;
        this.category = category;
    }

    public Item(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Item(String nome, double preco, int quantidade, Category category) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.category = category;
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



    @Override
    public String toString() {
        return "Item{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                '}';
    }
}
