package br.com.allef_macaxeira.appanimais;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView botaoCao;
    private ImageView botaoGato;
    private ImageView botaoLeao;
    private ImageView botaoMacaco;
    private ImageView botaoVaca;
    private ImageView botaoCabra;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoCabra = findViewById(R.id.cabraID);
        botaoCao = findViewById(R.id.caoID);
        botaoGato = findViewById(R.id.gatoID);
        botaoLeao = findViewById(R.id.leaoID);
        botaoMacaco = findViewById(R.id.macacoID);
        botaoVaca = findViewById(R.id.vacaID);

        botaoCabra.setOnClickListener(this);
        botaoCao.setOnClickListener(this);
        botaoGato.setOnClickListener(this);
        botaoLeao.setOnClickListener(this);
        botaoMacaco.setOnClickListener(this);
        botaoVaca.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.caoID:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.cao);
                tocarSom();
                break;
            case R.id.cabraID:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.ovelha);
                tocarSom();
                break;
            case R.id.gatoID:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.gato);
                tocarSom();
                break;
            case R.id.macacoID:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.macaco);
                tocarSom();
                break;
            case R.id.leaoID:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.leao);
                tocarSom();
                break;
            case R.id.vacaID:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.vaca);
                tocarSom();
                break;
        }
    }

    public void tocarSom(){
        if(mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
