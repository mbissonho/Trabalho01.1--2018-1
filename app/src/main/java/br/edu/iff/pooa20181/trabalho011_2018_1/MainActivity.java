package br.edu.iff.pooa20181.trabalho011_2018_1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tArea;
    private Button bSubmit;
    private RadioButton rbLt;
    private RadioButton rbGl;
    private RadioButton rbMelhor;

    private final double areaPorLata = 108;
    private final double areaPorGalao = 21.6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.fantasy_app_name));

        this.tArea = findViewById(R.id.tArea);
        this.bSubmit = findViewById(R.id.bSubmit);
        this.rbLt = findViewById(R.id.rbLt);
        this.rbGl = findViewById(R.id.rbGl);
        this.rbMelhor = findViewById(R.id.rbMelhor);

        this.bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                String message = "", title = "";

                if(tArea.getText().toString().trim().isEmpty()){

                    title = getString(R.string.dialog_title_err);
                    message = getString(R.string.dialog_message_err_empty);

                }else{

                    double area = Double.parseDouble(tArea.getText().toString());

                    if(rbLt.isChecked()){
                        title = getString(R.string.dialog_title_latas);

                        float arr[] = calculoLt(area);

                        message = (int) arr[0]+" Lata(s)\n"+
                        arr[1]+" m² de Capacidade\n"+
                        "Por: "+(arr[0]*80)+" reais";

                    }else if(rbGl.isChecked()){
                        title = getString(R.string.dialog_title_galoes);

                        float arr[] = calculoGl(area);

                        String name = (arr[0] <= 1 ? "Galão" : "Galões");

                        message = (int) arr[0]+" "+name+"\n"+
                        arr[1]+" m² de Capacidade\n"+
                        "Por: "+(arr[0]*25)+" reais";

                    }else if(rbMelhor.isChecked()){
                        title = getString(R.string.dialog_title_mix);

                        float arr[] = calculoMelhor(area);

                        String name = (arr[1] <= 1 ? "Galão" : "Galões");

                        message =
                        (int) arr[0]+" Lata(s)\n"+
                        (int) arr[1]+" "+name+"\n"+
                        arr[2]+" m² de Capacidade\n"+
                        "Por: "+((arr[0] * 80) + (arr[1] * 25))+" reais";



                    }else{
                        title = getString(R.string.dialog_title_err);
                        message = getString(R.string.dialog_message_err_rb_unset);
                    }

                    dialog.setTitle(title);
                    dialog.setMessage(message);
                    dialog.setNeutralButton("OK",null);
                    dialog.show();
                }
            }
        });

    }

    //Métodos

    private float[] calculoLt(Double area){

        float[] res = new float[2];

        res[0] = (float) Math.ceil(area / areaPorLata);
        res[1] = (float)(res[0] * areaPorLata);

        return res;
    }

    private float[] calculoGl(Double area){

        float[] res = new float[2];

        res[0] = (float) Math.ceil(area / areaPorGalao);
        res[1] = (float)(res[0] * areaPorGalao);

        return res;
    }

    private float[] calculoMelhor(Double area){

        float[] res = new float[3];


         if(area > (float)(3*areaPorGalao)){

            float qtdLt = (float)(area / areaPorLata);
            float qtdGl = 0;

            if(area < (float) areaPorLata){
                qtdLt = 1;
            }else{
                qtdLt = (float) Math.floor(qtdLt);
            }

            res[0] = qtdLt;

            float restof = (float) (area - (areaPorLata * qtdLt));

            if((restof > (float) 0)){

                if(restof < (float)areaPorGalao){
                    qtdGl = 1;
                }else{

                    qtdGl = (restof / (float) areaPorGalao);

                    if(restof%(float)areaPorGalao!=0){
                        qtdGl += 1;
                    }
                    qtdGl = (float) Math.floor(qtdGl);
                }
            }

            res[1] = qtdGl;
            res[2] = (float)((qtdLt * areaPorLata) + (qtdGl * areaPorGalao));

         }else if(area <= (float)(3*areaPorGalao)){ //64.8

             //Nenhuma Lata
             res[0] = 0;

             if(area <= areaPorGalao){
                res[1] = 1;
                res[2] = 25;
             }else{

                 float qtdGl = (float) (area / areaPorGalao);
                 qtdGl = (float) Math.floor(qtdGl);

                 float restof = (float) (area %areaPorGalao);
                 int restoi = (int)(area % areaPorGalao);

                 if((restof > (float) 0) && (restoi > (int) 0)){
                     qtdGl += 1;
                 }

                 res[1] = qtdGl;
                 res[2] = qtdGl * 25;
             }
         }

        return res;
    }


}
