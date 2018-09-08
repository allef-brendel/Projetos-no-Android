package br.com.macaxeiratec.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.macaxeiratec.adapter.TabAdapter;
import br.com.macaxeiratec.config.ConfiguracaoFirebase;
import br.com.macaxeiratec.helper.Base64Custom;
import br.com.macaxeiratec.helper.Preferencias;
import br.com.macaxeiratec.helper.R;
import br.com.macaxeiratec.helper.SlidingTabLayout;
import br.com.macaxeiratec.modelo.Contato;
import br.com.macaxeiratec.modelo.Usuario;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private Toolbar toolbar;
    private DatabaseReference firebase;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private String identificadorDoContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     autenticacao = ConfiguracaoFirebase.getFireBaseAutenticacao();

        toolbar = findViewById(R.id.toolbar);
        slidingTabLayout = findViewById(R.id.sltl);
        viewPager = findViewById(R.id.vp_page);

        toolbar.setTitle("ClanChat");
        setSupportActionBar(toolbar);

        //Configurar sliding tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.colorAccent));

        //Configurar adaptador
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_sair:
                delogarUsuario();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.item_adicionar:
                abrirCadastroUsuario();
                return true;
             default:
                 return super.onOptionsItemSelected(item);
        }
    }

    public void abrirCadastroUsuario(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        //Configuraçao do dialog
        alertDialog.setTitle("Novo Contato");
        alertDialog.setMessage("E-mail do usuario");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(MainActivity.this);
        alertDialog.setView(editText);

        //Configurar botoes
        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String emailContato = editText.getText().toString();
                
                //Validar o email contato
                if (emailContato.isEmpty()){
                    Toast.makeText(MainActivity.this, "Preencha o e-mail", Toast.LENGTH_SHORT).show();
                }else{
                    //Verificar se o email ja existe no banco de dados
                    identificadorDoContato = Base64Custom.codificarBase64(emailContato);

                    //Recuperar a instacia do firebase
                    firebase = ConfiguracaoFirebase.getReference().child("usuarios").child(identificadorDoContato);

                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){

                                //Recuperar dados do Usuario a ser adicionado
                                Usuario usuarioContato = dataSnapshot.getValue(Usuario.class);

                                //Recuperar identificador Usuario Logado(Base64)
                                Preferencias preferencias = new Preferencias(MainActivity.this);
                                String identificadorUsuarioLogado = preferencias.getIdentificador();

                                firebase = ConfiguracaoFirebase.getReference();
                                firebase = firebase.child("contatos").child(identificadorUsuarioLogado).child(identificadorDoContato);

                                Contato contato = new Contato();
                                contato.setIdentificadorUsuario(identificadorDoContato);
                                contato.setEmail(usuarioContato.getEmail());
                                contato.setNome(usuarioContato.getNome());

                                firebase.setValue(contato);



                            }else{
                                Toast.makeText(MainActivity.this, "Usuario não possui cadastro", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    public void delogarUsuario(){
        autenticacao.signOut();
        Intent intent = new Intent(MainActivity.this,ActivityLogin.class);
        startActivity(intent);
        finish();
    }
}
