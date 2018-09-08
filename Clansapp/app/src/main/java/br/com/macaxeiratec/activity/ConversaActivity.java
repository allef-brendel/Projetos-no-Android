package br.com.macaxeiratec.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.macaxeiratec.adapter.MensagemAdapter;
import br.com.macaxeiratec.config.ConfiguracaoFirebase;
import br.com.macaxeiratec.helper.Base64Custom;
import br.com.macaxeiratec.helper.Preferencias;
import br.com.macaxeiratec.helper.R;
import br.com.macaxeiratec.modelo.Mensagem;

public class ConversaActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private EditText editMensagem;
    private ImageButton btMensagem;
    private DatabaseReference firebase;
    private ListView listView;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;
    private ValueEventListener valueEventListenerMensagem;

    // dados do destinatÃ¡rio
    private String nomeUsuarioDestinatario;
    private String idUsuarioDestinatario;

    // dados do rementente
    private String idUsuarioRemetente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = findViewById(R.id.tb_conversa);
        editMensagem = findViewById(R.id.edit_mensagem);
        btMensagem = findViewById(R.id.bt_enviar);
        listView = findViewById(R.id.lv_conversas);

        // dados do usuÃ¡rio logado
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioRemetente = preferencias.getIdentificador();

        Bundle extra = getIntent().getExtras();

        if( extra != null ){
            nomeUsuarioDestinatario = extra.getString("nome");
            String emailDestinatario = extra.getString("email");
            idUsuarioDestinatario = Base64Custom.codificarBase64( emailDestinatario );
        }

        // Configura toolbar
        toolbar.setTitle( nomeUsuarioDestinatario );
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        // Monta listview e adapter
        mensagens = new ArrayList<>();
        adapter = new MensagemAdapter(ConversaActivity.this, mensagens);
        listView.setAdapter( adapter );

        // Recuperar mensagens do Firebase
        firebase = ConfiguracaoFirebase.getReference()
                .child("mensagens")
                .child( idUsuarioRemetente )
                .child( idUsuarioDestinatario );

        // Cria listener para mensagens
        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Limpar mensagens
                mensagens.clear();

                // Recupera mensagens
                for ( DataSnapshot dados: dataSnapshot.getChildren() ){
                    Mensagem mensagem = dados.getValue( Mensagem.class );
                    mensagens.add( mensagem );
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        firebase.addValueEventListener( valueEventListenerMensagem );

        // Enviar mensagem
        btMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoMensagem = editMensagem.getText().toString();

                if( textoMensagem.isEmpty() ){
                    Toast.makeText(ConversaActivity.this, "Digite uma mensagem para enviar!", Toast.LENGTH_LONG).show();
                }else{

                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario( idUsuarioRemetente );
                    mensagem.setMensagem( textoMensagem );

                    //Salvar mensagem para o usuario
                    salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario , mensagem );

                    //Salvar mensagem para o remetente
                    salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente , mensagem );

                    editMensagem.setText("");
                }
            }
        });
    }

    private boolean salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagem){
        try {

            firebase = ConfiguracaoFirebase.getReference().child("mensagens");

            firebase.child( idRemetente )
                    .child( idDestinatario )
                    .push()
                    .setValue( mensagem );

            return true;

        }catch ( Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerMensagem);
    }
}
