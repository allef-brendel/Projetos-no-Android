package br.com.macaxeiratec.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.com.macaxeiratec.config.ConfiguracaoFirebase;
import br.com.macaxeiratec.helper.Base64Custom;
import br.com.macaxeiratec.helper.Preferencias;
import br.com.macaxeiratec.modelo.Usuario;

import br.com.macaxeiratec.helper.R;

public class ActivityLogin extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button botaoLogar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);

        verificarUsuarioLogado();

        email = findViewById(R.id.editeText_login_email);
        senha = findViewById(R.id.editText_login_senha);
        botaoLogar = findViewById(R.id.botaoLogin);

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();
            }
        });
    }

    private  void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFireBaseAutenticacao();
        if(autenticacao.getCurrentUser() != null){
            abriTelaPrincipal();
        }
    }

    private void validarLogin(){

    autenticacao = ConfiguracaoFirebase.getFireBaseAutenticacao();
    autenticacao.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){

                Preferencias preferencias = new Preferencias(ActivityLogin.this);
                String identificadorUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmail());
                preferencias.salvarDados(identificadorUsuarioLogado);
                abriTelaPrincipal();

                Toast.makeText(ActivityLogin.this, "Sucesso ao logar", Toast.LENGTH_SHORT).show();
            }else{
                String erroExecao = "";

                try {
                    throw task.getException();
                }catch (FirebaseAuthInvalidUserException e){
                    erroExecao = "E-mail invalido";
                } catch (FirebaseAuthInvalidCredentialsException e) {
                    erroExecao = "Senha incorreta";
            } catch (Exception e) {
                    erroExecao = "Erro ao efetuar o login";
                    e.printStackTrace();
                }
                Toast.makeText(ActivityLogin.this, erroExecao, Toast.LENGTH_SHORT).show();
            }
    }});
    }

    public void abriTelaPrincipal(){
        Intent intent = new Intent(ActivityLogin.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirCadastroUsuario(View view){

        Intent intent = new Intent(ActivityLogin.this,CadastroActivity.class);
        startActivity(intent);
    }
}
