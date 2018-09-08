package br.com.allef_macaxeira.radiobutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private Button botaoEscolher;
    private RadioGroup radioGroup;
    private RadioButton radioButtonEscolhido;
    private TextView textoExibicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoEscolher = findViewById(R.id.botaoEscolherID);
        radioGroup = findViewById(R.id.radioGroupID);
        textoExibicao = findViewById(R.id.textoID);

        botaoEscolher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idRadioButtonEscolhido = radioGroup.getCheckedRadioButtonId();
                if (idRadioButtonEscolhido > 0){
                    radioButtonEscolhido = findViewById(idRadioButtonEscolhido);
                    textoExibicao.setText( radioButtonEscolhido.getText());
                }
            }
        });
    }
}
