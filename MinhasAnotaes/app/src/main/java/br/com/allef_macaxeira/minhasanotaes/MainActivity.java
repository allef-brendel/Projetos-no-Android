package br.com.allef_macaxeira.minhasanotaes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private ImageView botaoSalvar;
    private EditText textoSalvar;
    private static final String NOME_ARQUIVO = "arquivo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoSalvar = findViewById(R.id.botaoSalvarID);
        textoSalvar = findViewById(R.id.textoID);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoDigitado = textoSalvar.getText().toString();
                gravarArquivo(textoDigitado);
                Toast.makeText(MainActivity.this, "Anotação salva com Sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        //Recuperar Arquivo
        if(lerArquivo() != null){
            textoSalvar.setText(lerArquivo());
        }

        }

    private void gravarArquivo(String texto){
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(NOME_ARQUIVO, Context.MODE_PRIVATE));
            outputStreamWriter.write(texto);
            outputStreamWriter.close();
        }catch(IOException e){
            Log.v("MainActivity ", e.toString());
        }
    }

    private String lerArquivo(){
        String resultado = "";
        try{

            //Abrir Arquivo
            InputStream arquivo = openFileInput(NOME_ARQUIVO);
            if(arquivo != null){

                //Ler arquivo
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);

                //Gerar um buffer do arquivo lido
                BufferedReader buffer = new BufferedReader(inputStreamReader);

                // Recuperar texto do arquivo
                String linhaDoArquivo = "";
                while((linhaDoArquivo = buffer.readLine()) != null){
                    resultado += linhaDoArquivo;
                }

                arquivo.close();
            }



        }catch(IOException e){
            Log.v("MainActivity ",e.toString());
        }
        return resultado;
    }
}
