package br.com.allef_macaxeira.caraoucoroa;

import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;

        import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView botaoJogar;
    private String[] itens = {"cara","coroa"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoJogar = findViewById(R.id.jogarID);

        botaoJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random radomico = new Random();
                int numero = radomico.nextInt(2);

                Intent intent = new Intent(MainActivity.this, SegundoActivity.class);
                intent.putExtra("opcao", itens[numero]);
                startActivity(intent);
            }
        });
    }
}
