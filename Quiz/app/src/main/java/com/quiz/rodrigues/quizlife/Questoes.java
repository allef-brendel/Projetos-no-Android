package com.quiz.rodrigues.quizlife;


public class Questoes {

    public String nQuestoes[] = {

            "Qual das alternativas é um componente de entrada e saida do computador2?",
            "Qual das alternativas é um componente de entrada e saida do computador3?",
            "Qual das alternativas é um componente de entrada e saida do computador4?",
            "Qual das alternativas é um componente de entrada e saida do computador5?",
            "Qual das alternativas é um componente de entrada e saida do computador6?"
    };

    private String nEscolhas[][] = {

            {"Gabinete","CPU","Monitor","Teclado","Monitor"},
            {"Teclado","Gabinete","Monitor","CPU","Monitor"},
            {"Monitor","Gabinete","CPU","Teclado","Monitor"},
            {"CPU","Monitor","Gabinete","Teclado","Monitor"},
            {"Gabinete","CPU","Teclado","Monitor","Monitor"}


    };

    private String nRespostaCorreta(int a){
        return nEscolhas[a][4];
    }

    public String getQuestao(int a) {
        String questao = nQuestoes[a];
        return questao;
    }

    public String getEscolha1(int a) {
        String escolha = nEscolhas[a][0];
        return escolha;
    }

    public String getEscolha2(int a) {
        String escolha = nEscolhas[a][1];
        return escolha;
    }

    public String getEscolha3(int a) {
        String escolha = nEscolhas[a][2];
        return escolha;
    }

    public String getEscolha4(int a) {
        String escolha = nEscolhas[a][3];
        return escolha;
    }

    public String getRespostaCorreta(int a) {
        return nEscolhas[a][4];

    }
}