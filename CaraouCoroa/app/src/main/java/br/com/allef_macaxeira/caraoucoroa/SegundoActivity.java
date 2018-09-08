package br.com.allef_macaxeira.caraoucoroa;

import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SegundoActivity extends AppCompatActivity {

    private ImageView voltar;
    private ImageView moedaCara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo);

        voltar = findViewById(R.id.voltarID);
        moedaCara = findViewById(R.id.caraID);

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            String opcaoMoeda = extra.getString("opcao");
            if (opcaoMoeda.equals("cara")){
                moedaCara.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moeda_cara));
            }else{
                moedaCara.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.moeda_coroa));
            }
        }
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SegundoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
