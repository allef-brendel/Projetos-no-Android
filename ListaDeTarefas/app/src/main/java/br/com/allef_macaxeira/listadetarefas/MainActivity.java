package br.com.allef_macaxeira.listadetarefas;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button botaoAdcionar;
    private EditText editText;
    private ListView listView;
    private SQLiteDatabase bancoDados;

    private AlertDialog.Builder dialog;

    private ArrayAdapter<String> itensAdaptador;
    private ArrayList<String> itens;
    private ArrayList<Integer> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            botaoAdcionar = findViewById(R.id.botaoID);
            editText = findViewById(R.id.editTextID);
            listView = findViewById(R.id.listVIewID);

            //Criar banco de dados
            bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            //Criar Tabela
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tarefas(id INTEGER PRIMARY KEY AUTOINCREMENT,tarefa VARCHAR)");

            //Criar botao salvar
            botaoAdcionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String textoDigitado = editText.getText().toString();
                    salvarTarefas(textoDigitado);
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    final int indice = i;
                    //criar dialog
                    dialog = new AlertDialog.Builder(MainActivity.this);

                    //configurar o titulo
                    dialog.setTitle("Excluir");

                    //configurar a mensagem
                    dialog.setMessage("Deseja Excluir?");

                    //caixa de dialogo nao fecha e coloca icone na caixa
                    dialog.setCancelable(false);
                    dialog.setIcon(android.R.drawable.ic_delete);

                    //configurar botoes sim e nao
                    dialog.setNegativeButton("Não",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int b) {

                                }
                            });
                    dialog.setPositiveButton("Sim",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int b) {

                                    //Remover tarefas clicando
                                    removerTarefa(ids.get(indice));
                                }
                            });
                    dialog.create().show();
                }
            });

            //recuperar tarefas
            recuperarTarefas();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void salvarTarefas(String texto) {
        try {

            if(texto.equals("")){
                Toast.makeText(this, "Digite uma tarefa", Toast.LENGTH_SHORT).show();
            }else {
                bancoDados.execSQL("INSERT INTO tarefas(tarefa)VALUES('" + texto + "')");
                Toast.makeText(this, "Tarefa salva com sucesso", Toast.LENGTH_SHORT).show();
                recuperarTarefas();
                editText.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void recuperarTarefas(){
        try{
            //Recuperar as tarefas
            Cursor cursor = bancoDados.rawQuery("SELECT * FROM tarefas ORDER BY id DESC",null);

            //Recuperar os ids das colunas
            int recuperarIndiceID = cursor.getColumnIndex("id");
            int recuperarIndiceTarefa = cursor.getColumnIndex("tarefa");

            //Instanciar ArrayList
            ids = new ArrayList<Integer>();
            itens = new ArrayList<String>();

            //Criar adaptador
            itensAdaptador = new ArrayAdapter<String>(getApplicationContext(),
                                                                android.R.layout.simple_list_item_2,
                                                                android.R.id.text2,
                                                                itens);
            listView.setAdapter(itensAdaptador);

            //Listar as tarefas
            cursor.moveToFirst();
            while(cursor != null){
                //Exibir tarefas
                Log.i("resultado - ","tarefa: "+ cursor.getString(recuperarIndiceTarefa));
                itens.add(cursor.getString(recuperarIndiceTarefa));
                ids.add(Integer.parseInt(cursor.getString(recuperarIndiceID)));
                cursor.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void removerTarefa(Integer id){
        try{
            bancoDados.execSQL("DELETE FROM tarefas WHERE id = " + id);
            recuperarTarefas();
            Toast.makeText(this, "Tarefa deletada com sucesso", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
