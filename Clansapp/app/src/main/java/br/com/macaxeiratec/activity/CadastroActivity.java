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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import br.com.macaxeiratec.config.ConfiguracaoFirebase;
import br.com.macaxeiratec.helper.Base64Custom;
import br.com.macaxeiratec.helper.Preferencias;
import br.com.macaxeiratec.helper.R;
import br.com.macaxeiratec.modelo.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText cadastroEmail;
    private EditText cadastroSenha;
    private EditText cadastroNome;
    private Button   botaoCadastra;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        cadastroEmail = findViewById(R.id.editeText_cadastro_email);
        cadastroNome = findViewById(R.id.editeText_cadastro_nome);
        cadastroSenha = findViewById(R.id.editeText_cadastro_senha);
        botaoCadastra = findViewById(R.id.botaoCadastrar);

        botaoCadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = new Usuario();
                usuario.setEmail(cadastroEmail.getText().toString());
                usuario.setSenha(cadastroSenha.getText().toString());
                usuario.setNome(cadastroNome.getText().toString());
                cadastrarUsuario();

            }
        });
    }

    public void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFireBaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(CadastroActivity.this, "Conta cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = task.getResult().getUser();

                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setId(identificadorUsuario);
                    usuario.salvar();

                    Preferencias preferencias = new Preferencias(CadastroActivity.this);
                    preferencias.salvarDados(identificadorUsuario);

                    abrirLoginUsuario();
                }else{
                    String erroExecao = "";

                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExecao = "Erro ao cadastrar. Digite uma senha com no minimo 6 caracteres, contendo letras e números";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExecao = "O e-mail digitado é invalido, digite um novo e-mail";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExecao = "Já existe uma conta criada com esse e-mail";
                    } catch (Exception e) {
                        erroExecao = "Erro ao efetuar cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, erroExecao, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this,ActivityLogin.class);
        startActivity(intent);
        finish();
    }
}
