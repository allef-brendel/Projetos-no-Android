package br.com.allef_macaxeira.seekbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {


    private SeekBar seekBar;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.barraID);
        imageView = findViewById(R.id.imageViewID);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int valorSelecionado = i;
                if (valorSelecionado == 1){
                imageView.setImageResource(R.drawable.pouco);
                }else if(valorSelecionado == 2){
                    imageView.setImageResource(R.drawable.medio);
                }else if(valorSelecionado == 3){
                    imageView.setImageResource(R.drawable.muito);
                }else{
                    imageView.setImageResource(R.drawable.susto);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
