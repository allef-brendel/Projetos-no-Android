package br.com.macaxeiratec.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.macaxeiratec.activity.ConversaActivity;
import br.com.macaxeiratec.activity.MainActivity;
import br.com.macaxeiratec.adapter.ContatoAdapter;
import br.com.macaxeiratec.config.ConfiguracaoFirebase;
import br.com.macaxeiratec.helper.Preferencias;
import br.com.macaxeiratec.helper.R;
import br.com.macaxeiratec.modelo.Contato;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Contato> contatos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerContatos;

    public ContatosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerContatos);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerContatos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //instanciar arraylist
        contatos = new ArrayList<>();


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        //Montar listViwe e adapter
        listView = view.findViewById(R.id.lv_contatos);
        adapter = new ArrayAdapter(getActivity(),R.layout.lista_contato,contatos);

        adapter = new ContatoAdapter(getActivity(),contatos);
        listView.setAdapter(adapter);

        //Recuperar contatos firebase
        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorUsuarioLogado = preferencias.getIdentificador();

        firebase = ConfiguracaoFirebase.getReference().child("contatos").child(identificadorUsuarioLogado);

        //listener para recuperar contatos
        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Limpar lista
                contatos.clear();

                //Listar contatos
                for (DataSnapshot dados: dataSnapshot.getChildren()){

                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                //Recuperar dados a serem passados
                Contato contato = contatos.get(position);

                //recuperar dados para conversa activity
                intent.putExtra("nome",contato.getNome());
                intent.putExtra("email",contato.getEmail());

                startActivity(intent);
            }
        });

        return view;
    }

}
