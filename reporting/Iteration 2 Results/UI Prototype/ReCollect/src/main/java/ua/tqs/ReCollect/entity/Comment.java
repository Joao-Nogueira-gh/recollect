package ua.tqs.ReCollect.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Comment {

    private Long idAutor;
    private String nomeAutor;
    private Timestamp timestamp;
    private String conteudo;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Comment() {

    }

    public Comment(String nomeAutor, String conteudo) {
        this.nomeAutor = nomeAutor;
        this.conteudo = conteudo;

        Date date = new Date();
        long time = date.getTime();
        this.timestamp = new Timestamp(time);
    }

    public String getnomeAutor() {
        return nomeAutor;
    }

    public void setnomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getData() {
        Date d = Date.from(this.timestamp.toInstant());

        return d.toString();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "nomeAutor='" + nomeAutor + '\'' +
                ", timestamp=" + timestamp +
                ", conteudo='" + conteudo + '\'' +
                '}';
    }
}