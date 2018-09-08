package br.com.allef_frasedodia.frasedodia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button botaoNovaFrase;
    private TextView textoNovaFrase;
    private String[] frases = {"Nada melhor para a alma que treinar a gratidão e diariamente agradecer pelas bênçãos que receber.",
            "Amar a vida é amar seus amigos, pois sem eles nada mais faz sentido no seu dia a dia.",
            "Nem toda mudança importante acontece de repente e faz barulho, algumas são silenciosas e vão se fazendo no dia a dia.",
            "Desejo uma boa semana a todos aqueles que diariamente persistem em lutar pelos seus objetivos!"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoNovaFrase = findViewById(R.id.botaoNovaFraseID);
        textoNovaFrase = findViewById(R.id.textoNovaFraseID);

        botaoNovaFrase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Random random = new Random();
                int numero = random.nextInt(4);

                textoNovaFrase.setText(frases[numero]);
            }
        });


    }
}
