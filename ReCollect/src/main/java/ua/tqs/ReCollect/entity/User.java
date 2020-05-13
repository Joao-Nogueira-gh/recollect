package ua.tqs.ReCollect.entity;

import javax.persistence.*;
import java.util.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String password;

    @OneToOne(targetEntity= Localizacao.class, fetch=FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Localizacao localizacao;

    // usei Set porque nao podem ser repetidos e por que com List<> não
    // dá para usar FetchType.EAGER em mais do que uma
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Item> itensVendidos;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Item> itensFavoritos;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Item> itensPublicados;

    public User() {
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.itensVendidos = new HashSet<>();
        this.itensFavoritos = new HashSet<>();
        this.itensPublicados = new HashSet<>();
    }

    public User(String name, String password, String email, Localizacao localizacao) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.localizacao = localizacao;
        this.itensVendidos = new HashSet<>();
        this.itensFavoritos = new HashSet<>();
        this.itensPublicados = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Set<Item> getItensVendidos() {
        return itensVendidos;
    }

    public void setItensVendidos(Set<Item> itensVendidos) {
        this.itensVendidos = itensVendidos;
    }

    public Set<Item> getItensFavoritos() {
        return itensFavoritos;
    }

    public void setItensFavoritos(Set<Item> itensFavoritos) {
        this.itensFavoritos = itensFavoritos;
    }

    public Set<Item> getItensPublicados() {
        return itensPublicados;
    }

    public void setItensPublicados(Set<Item> itensPublicados) {
        this.itensPublicados = itensPublicados;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", localizacao=" + localizacao +
                ", itensVendidos=" + itensVendidos +
                ", itensFavoritos=" + "itensFavoritos" +
                ", itensPublicados=" + "itensPublicados" +
                '}';
    }
}
