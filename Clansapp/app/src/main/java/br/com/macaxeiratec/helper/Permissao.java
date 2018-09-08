package br.com.macaxeiratec.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validarPermissoes(int requestCode, Activity activity, String[] permissoes){
        if(Build.VERSION.SDK_INT >=23){

            List<String> listaDePermissoes = new ArrayList<>();

            //Percorrer as permissoes necessarias, verificar se ja tem a permissao liberada
            for(String permissao: permissoes){
               Boolean validarPermissao =  ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
               if(!validarPermissao) listaDePermissoes.add(permissao);
            }
            //Caso a lista esteja vazia, nao é necessario solicitar permissao
            if(listaDePermissoes.isEmpty()) return true;

            String[] novasPermissoes = new String[listaDePermissoes.size()];
            listaDePermissoes.toArray(novasPermissoes);

            //Solicitar permissao
            ActivityCompat.requestPermissions(activity,novasPermissoes,requestCode);
        }
        return true;
    }
}
