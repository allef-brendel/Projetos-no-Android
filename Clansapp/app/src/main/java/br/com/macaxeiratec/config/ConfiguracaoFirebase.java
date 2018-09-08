package br.com.macaxeiratec.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    private static DatabaseReference firebaseReferemcia;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getReference(){

        if(firebaseReferemcia == null) {
            firebaseReferemcia = FirebaseDatabase.getInstance().getReference();
        }
        return firebaseReferemcia;
    }

    public static FirebaseAuth getFireBaseAutenticacao(){

        if(autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }
}
