package br.com.macaxeiratec.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import br.com.macaxeiratec.helper.Preferencias;
import br.com.macaxeiratec.helper.R;
import br.com.macaxeiratec.modelo.Mensagem;

public class MensagemAdapter extends ArrayAdapter<Mensagem> {

    private Context context;
    private ArrayList<Mensagem> mensagens;

    public MensagemAdapter(@NonNull Context c, @NonNull ArrayList<Mensagem> mensagens) {
        super(c , 0, mensagens);
        this.context = c;
        this.mensagens = mensagens;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        //Verificar se a lista esta vazia
        if (mensagens != null){
            //Recuperar os dados do remetente
            Preferencias preferencias = new Preferencias(context);
            String idUsuarioRemetente = preferencias.getIdentificador();

            //Inicializar o objeto para montagem da layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Recuperar mensagem
            Mensagem mensagem = mensagens.get(position);

            //montar viwe a partir do xml
            if (idUsuarioRemetente.equals(mensagem.getIdUsuario())){
                view = inflater.inflate(R.layout.item_mensagem_direita,parent,false);
            }else{
                view = inflater.inflate(R.layout.item_mensagem_esquerda,parent,false);
            }

            //Recuperar elemento para exibição
            TextView textoMensagem = view.findViewById(R.id.tv_mensagem);
            textoMensagem.setText(mensagem.getMensagem());

        }

        return view;

    }
}
