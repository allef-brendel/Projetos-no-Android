package br.com.allef_macaxeira.firebaseapp;

public class Produto {

    private String descricao;
    private String cor;
    private String fabricante;

    public Produto() {
    }

    public String getNome() {
        return descricao;
    }

    public void setNome(String nome) {
        this.descricao = nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}
