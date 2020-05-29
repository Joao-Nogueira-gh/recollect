package ua.tqs.ReCollect.utils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

public class CommentForm {

    @NotNull
    @NotEmpty
    private String conteudo;

    public CommentForm(String conteudo) {
        this.conteudo = conteudo;
    }


    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
