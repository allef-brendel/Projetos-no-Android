package br.com.allef_macaxeira.salvarcor;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button botaoSalvar;
    private RelativeLayout layout;
    private static final String ARQUIVO_PREFERENCIA = "ArqPreferencia";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoSalvar = findViewById(R.id.botaoSalvarID);
        radioGroup = findViewById(R.id.radioGroupID);
        layout = findViewById(R.id.layoutID);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioButtonEscolhido = radioGroup.getCheckedRadioButtonId();

                if (radioButtonEscolhido>0){
                    radioButton  = findViewById(radioButtonEscolhido);

                    SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String corEscolhida = radioButton.getText().toString();
                    editor.putString("corEscolhida", corEscolhida);
                    editor.commit();
                    setBackground(corEscolhida);
                }
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
        if(sharedPreferences.contains("corEscolhida")){
            String corRecuperada = sharedPreferences.getString("corEscolhida", "Amarelo");
            setBackground(corRecuperada);
        }
    }
    private void setBackground(String cor){
        if (cor.equals("Azul")){
            layout.setBackgroundColor(Color.parseColor("#495c7b"));
       }else if(cor.equals("Amarelo")){
            layout.setBackgroundColor(Color.parseColor("#ffce26"));
       }else if(cor.equals("Verde")){
            layout.setBackgroundColor(Color.parseColor("#009670"));
       }
    }
}
