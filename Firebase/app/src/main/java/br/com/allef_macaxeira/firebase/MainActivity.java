package br.com.allef_macaxeira.firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        //Verificar usuario logado
        if(firebaseAuth.getCurrentUser() != null){
            Log.i("Login","Usuario logado!!");
        }else{
            Log.i("Login","Usuario nao esta logado!!");
        }

        /*Login
        firebaseAuth.signInWithEmailAndPassword("allef.brendel@dce.ufpb.br","123456").addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("signUser","Sucesso ao logar usuario!!");
                }else{
                    Log.i("signUser","Erro ao logar usuario!!");
                }
            }
        });*/

        /*Cadastro
        firebaseAuth.createUserWithEmailAndPassword("allef.brendel@dce.ufpb.br","123456").addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("createUser","Conta criada com sucesso!!");
                }else{
                    Log.i("createUser","Erro ao criar usuario!!");
                }
            }
        });*/
    }
}
