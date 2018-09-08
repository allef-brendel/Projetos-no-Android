package br.com.allef_macaxeira.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {


            //Criar banco de dados
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            //Criar tabela
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas(nome VARCHAR, idade INT(3))");

            //Inserir dados na tabela
           // bancoDados.execSQL("INSERT INTO pessoas(nome,idade) VALUES('Welyson',20)");
           // bancoDados.execSQL("INSERT INTO pessoas(nome,idade) VALUES('Lucas',21)");
           // bancoDados.execSQL("INSERT INTO pessoas(nome,idade) VALUES('Fernando',22)");
           // bancoDados.execSQL("INSERT INTO pessoas(nome,idade) VALUES('Gerson',23)");
           // bancoDados.execSQL("INSERT INTO pessoas(nome,idade) VALUES('John',27)");
           // bancoDados.execSQL("INSERT INTO pessoas(nome,idade) VALUES('Arnaldo',20)");

            //Recuperar dados
            Cursor cursor = bancoDados.rawQuery("SELECT nome, idade FROM pessoas", null);

            //Recuperar indice das colunas
            int indiceDaColunaNome = cursor.getColumnIndex("nome");
            int indiceDaColunaIdade = cursor.getColumnIndex("idade");

            cursor.moveToFirst();
            while (cursor != null) {
                Log.i("RESULTADO - nome ", cursor.getString(indiceDaColunaNome));
                Log.i("RESULTADO - idade ", cursor.getString(indiceDaColunaIdade));
                cursor.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}