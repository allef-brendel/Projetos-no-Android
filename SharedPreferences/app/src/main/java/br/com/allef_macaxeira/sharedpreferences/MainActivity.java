package br.com.allef_macaxeira.sharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textoResultado;
    private Button botaoSalvar;
    private EditText textoNome;

    private static final String ARQUIVO_PREFERENCE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoNome = findViewById(R.id.editTextID);
        textoResultado = findViewById(R.id.textoResultadoID);
        botaoSalvar = findViewById(R.id.botaoSalvarID);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCE,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(textoNome.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Por favor, preencher o nome", Toast.LENGTH_SHORT).show();
                }else{
                    editor.putString("nome",textoNome.getText().toString());
                    editor.commit();
                    textoResultado.setText("Olá, "+textoNome.getText().toString());
                }
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCE,0);
        if(sharedPreferences.contains("nome")){
            String nomeUsuario = sharedPreferences.getString("nome","Usuario não definido");
            textoResultado.setText("Olá "+nomeUsuario);
        }else{
            textoResultado.setText("Olá, usuario não definido");
        }
    }
}
