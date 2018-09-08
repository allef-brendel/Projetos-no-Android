package br.com.allef_macaxeira.appsignos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listaViwe;
    private String[] listaSignos = {"Aries","Touro","Gêmeos","Câncer",
                                    "Leão","Virgem","Libra","Escorpião",
                                    "Sagitario","Capricornio","Aquario"};

    private String[] frases = {"Esse é o de Aries","Esse é o de Touro","Esse é o de Gêmeos",
                                "Esse é o de Câncer", "Esse é o de Leão","Esse é o de Virgem",
                                "Esse é o de Libra","Esse é o de Escorpião","Esse é o de Sagitario",
                                "Esse é o de Capricornio","Esse é o de Aquario"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaViwe.findViewById(R.id.listViewID);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                                                android.R.layout.simple_list_item_1,
                                                                android.R.id.text1,listaSignos);
        listaViwe.setAdapter(adapter);

        listaViwe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int posicao = i;
                Toast.makeText(getApplicationContext(),frases[posicao], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
