package br.com.allef_alcoolgasolina.alcoolougasolina;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText precoAlcool;
    private EditText precoGasolina;
    private Button botaoVerificar;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        precoAlcool = findViewById(R.id.precoAlcoolID);
        precoGasolina = findViewById(R.id.precoGasolinaID);
        botaoVerificar = findViewById(R.id.botaoVerificarID);
        resultado = findViewById(R.id.textoResultadoID);

        botaoVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gasolina = precoGasolina.getText().toString();
                String alcool = precoAlcool.getText().toString();

                double gasolinaPreco = Double.parseDouble(gasolina);
                double alcoolPreco = Double.parseDouble(alcool);

                double resu = alcoolPreco / gasolinaPreco;
                if(resu>=0.7){
                    resultado.setText("Vale mais em conta usar a Gasolina");
                }else{
                    resultado.setText("Vale mais em conta usar o Alcool");
                }
            }
        });
    }
}
