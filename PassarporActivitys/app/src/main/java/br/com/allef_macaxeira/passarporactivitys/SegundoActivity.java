package br.com.allef_macaxeira.passarporactivitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SegundoActivity extends AppCompatActivity {

    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo);

        texto = findViewById(R.id.textoID);

        Bundle extra = getIntent().getExtras();

        if(extra != null){
            String textoPassado = extra.getString("nome");
            texto.setText(textoPassado);
        }

    }
}
