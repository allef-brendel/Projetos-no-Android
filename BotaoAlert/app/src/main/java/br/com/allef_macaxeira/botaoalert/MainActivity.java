package br.com.allef_macaxeira.botaoalert;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button botao;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao = findViewById(R.id.botaoID);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Criar Alert Dialog
                dialog = new AlertDialog.Builder(MainActivity.this);

                //configurar o titulo
                dialog.setTitle("Excluir");

                //configurar a mensagem
                dialog.setMessage("Deseja Excluir?");

                //caixa de dialogo nao fecha e coloca icone na caixa
                dialog.setCancelable(false);
                dialog.setIcon(android.R.drawable.alert_dark_frame);

                //configurar botoes sim e nao
                dialog.setNegativeButton("Não",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "Botão Não", Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "Botão Sim", Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.create().show();

            }
        });
    }
}
