package br.com.allef_idadecachorro.idadedocachorro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button botaoIdade;
    private TextView resultadoIdade;
    private EditText inputUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoIdade = findViewById(R.id.botaoID);
        resultadoIdade = findViewById(R.id.resultadoID);
        inputUsuario = findViewById(R.id.numeroDigitadoID);

        botaoIdade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String numeroDigitado =  inputUsuario.getText().toString();
                if(numeroDigitado.isEmpty()){
                    resultadoIdade.setText("Digite um numero!");
                }else{
                    int resultado = Integer.parseInt(numeroDigitado);
                    resultado = resultado * 7;
                    resultadoIdade.setText(resultado + " Anos");

                }
            }
        });
    }
}
