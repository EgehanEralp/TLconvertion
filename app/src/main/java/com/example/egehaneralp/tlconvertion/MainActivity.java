package com.example.egehaneralp.tlconvertion;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class MainActivity extends AppCompatActivity{


    TextView tlText, dolarT, euroT, poundT, chfT, audT, cadT;
    Button button;
    String s1,s2,s3,s4,s5,s6;
    int a1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tlText= (TextView) findViewById(R.id.tlText);
        dolarT=(TextView) findViewById(R.id.dolarT);
        euroT=(TextView) findViewById(R.id.euroT);
        poundT=(TextView) findViewById(R.id.poundT);
        chfT=(TextView)findViewById(R.id.chfT);
        audT=(TextView)findViewById(R.id.audT);
        cadT=findViewById(R.id.cadT);

        button=findViewById(R.id.button);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ArkaPlan().execute("https://api.exchangeratesapi.io/latest?base=TRY");

                //ArkaPlan sınıf nesnesini çalıştırdım.

            }
        });
    }

    //Main class'a 1 adet class extend edebileceğim için sıkıntı çıkaracaktı.
    //bu yuzden inner class oluşturup, onun içinde doInBackground' ı çalıştıracağım.

    class ArkaPlan extends AsyncTask<String,Void,String> {     //Arka planda http den verileri almak
        protected String doInBackground(String... params) {
            HttpURLConnection connection;
            BufferedReader buf;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect(); //HATA BURADA
                InputStream is = connection.getInputStream();
                buf = new BufferedReader(new InputStreamReader(is));

                String satir, dosya ="";

                while ((satir = buf.readLine()) != null) {
                    //Log.d("satir", satir);
                    dosya += satir;  // WHİLE BİTTİGİNDE SUNUCUDAKİ TÜM SATİRLARI ELDE ETMİŞ OLUCAĞIM

                }
                return dosya;

            } catch (Exception e) {
                e.printStackTrace();

            }

            return "sorun";
        }

        @Override
        protected void onPostExecute(String s) { //execute metodu çalıştıktan sonra - doinbackground çalışır
                                                 //postExecute ise doInBackground işleminden sonra çalışır
                                                 //yani return edilen String değer buna parametre olarak gelir!!
                                                 //ve AsyncTask sınıfı metodu'dur, Override ederiz.

            //super.onPostExecute(s);
            try{
                JSONObject json = new JSONObject(s);//****
                String rates =json.getString("rates");
                JSONObject jo = new JSONObject(rates);

                s1=jo.getString("USD");
                dolarT.setText("Dolar = "+s1);

                s2=jo.getString("EUR");
                euroT.setText("Euro = "+s2);

                s3=jo.getString("GBP");
                poundT.setText("Pound = "+s3);

                s4=jo.getString("CHF");
                chfT.setText("CHF = "+s4);

                s5=jo.getString("AUD");
                audT.setText("AUD = "+s5);

                s6=jo.getString("CAD");
                cadT.setText("CAD = "+s6);

            }
            catch(Exception e){
                e.printStackTrace();
            }


        }
    }
}
