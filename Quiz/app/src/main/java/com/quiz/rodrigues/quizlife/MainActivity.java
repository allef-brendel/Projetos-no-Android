package com.quiz.rodrigues.quizlife;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button botaoEscolher;
    private RadioButton resposta1, resposta2, resposta3, resposta4;
    private RadioButton radioButtonEscolhido ;
    private RadioGroup radioGroup;
    private TextView questao, pontuacao;
    private Questoes nQuestoes = new Questoes();
    private String nResposta;
    private String pontuacaoString = "";
    private int nPontuacao = 0;
    private int nQuestoesLength = nQuestoes.nQuestoes.length;
    private int numeroRadom;
    private int numeroRadom2;
    private Random r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        r = new Random();

        resposta1 = findViewById(R.id.radioButton1);
        resposta2 = findViewById(R.id.radioButton2);
        resposta3 = findViewById(R.id.radioButton3);
        resposta4 = findViewById(R.id.radioButton4);

        radioButtonEscolhido = findViewById(R.id.radioButton1);
        radioGroup = findViewById(R.id.radioGroupID);
        botaoEscolher = findViewById(R.id.botaoEscolha);
        questao = findViewById(R.id.textViewID);
        pontuacao = findViewById(R.id.pontuacaoID);

        numeroRadom = r.nextInt(nQuestoesLength);
        atualizarQuestoes(numeroRadom);

       botaoEscolher.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               int idRadioButtonEscolhido = radioGroup.getCheckedRadioButtonId();
               if (idRadioButtonEscolhido > 0) {
                   radioButtonEscolhido = findViewById(idRadioButtonEscolhido);
                   if (nQuestoes.getRespostaCorreta(numeroRadom).equals(radioButtonEscolhido.getText())) {
                       nPontuacao++;
                       pontuacao.setText(pontuacaoString + nPontuacao);
                       radioButtonEscolhido.setBackgroundColor(Color.GREEN);
                       radioButtonEscolhido.setTextColor(Color.WHITE);
                       proximaPergunta();

                   } else {
                       radioButtonEscolhido.setBackgroundColor(Color.RED);
                       radioButtonEscolhido.setTextColor(Color.WHITE);
                       gameOver();
                   }
               }
           }
       });
    }

    private void atualizarQuestoes(int num){
        questao.setText(nQuestoes.getQuestao(num));
        resposta1.setText(nQuestoes.getEscolha1(num));
        resposta2.setText(nQuestoes.getEscolha2(num));
        resposta3.setText(nQuestoes.getEscolha3(num));
        resposta4.setText(nQuestoes.getEscolha4(num));

        nResposta = nQuestoes.getRespostaCorreta(num);

    }

    private void gameOver(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setMessage("Resposta Incorreta! Sua pontuação final é: " + nPontuacao + " Pontos!");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Novo jogo",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*Quando o usuario deseja iniciar um novo jogo
                        finaliza a activity antes de iniciar uma nova*/
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
        alertDialogBuilder.setNegativeButton("Finalizar Jogo",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }

                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        }

    private void proximaPergunta(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setMessage("Resposta Correta!");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Proxima Pergunta",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        numeroRadom2 = r.nextInt(nQuestoesLength);

                        if (numeroRadom == numeroRadom2) {
                            numeroRadom2 = r.nextInt(nQuestoesLength);
                            atualizarQuestoes(numeroRadom2);
                            radioButtonEscolhido.setBackgroundColor(Color.WHITE);
                            radioButtonEscolhido.setTextColor(Color.BLACK);

                        } else {
                            atualizarQuestoes(numeroRadom2);
                            radioButtonEscolhido.setBackgroundColor(Color.WHITE);
                            radioButtonEscolhido.setTextColor(Color.BLACK);
                        }

                    }
                });
        alertDialogBuilder.setNegativeButton("Finalizar Jogo",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }

                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}

