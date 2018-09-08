package br.com.allef_macaxeira.checkbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CheckBox checkBoxCao;
    private CheckBox checkBoxGato;
    private CheckBox checkBoxPapagaio;

    private Button  botaoMostrar;
    private TextView textoResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBoxCao = findViewById(R.id.checkBoxCaoID);
        checkBoxGato = findViewById(R.id.checkBoxGatoID);
        checkBoxPapagaio = findViewById(R.id.checkBoxPapagaioID);

        botaoMostrar = findViewById(R.id.botaoMostrarID);
        textoResultado = findViewById(R.id.textoResultadoID);

        botaoMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemSelecionado = "";
                itemSelecionado += "Item: " + checkBoxCao.getText() + " Status: " + checkBoxCao.isChecked() + "\n";
                itemSelecionado += "Item: " + checkBoxGato.getText() + " Status: " + checkBoxGato.isChecked() + "\n";
                itemSelecionado += "Item: " + checkBoxPapagaio.getText() + " Status: " + checkBoxPapagaio.isChecked() + "\n";

                textoResultado.setText(itemSelecionado);
            }
        });
    }
}
