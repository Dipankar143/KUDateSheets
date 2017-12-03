package in.ac.kuexam.kudatesheets;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    String s;
    RecyclerView recyclerView;
    ArrayList<data> arrayList;
    data myData;
    ProgressDialog progressDialog;
    private InterstitialAd interstitialAd;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ;
        recyclerView=(RecyclerView) findViewById(R.id.myResView);
        arrayList=new ArrayList<>();
        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }

        progressDialog.show();


        new task().execute();
        AdRequest newad=new AdRequest.Builder().build();
        interstitialAd=new InterstitialAd(getApplicationContext());
        interstitialAd.setAdUnitId("ca-app-pub-3325243029875259/5845021221");
        interstitialAd.loadAd(newad);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                displayAd();
                i++;
            }
            public void onAdClosed() {
                // Load the next interstitial.
                if (i<3) {
                    interstitialAd.loadAd(new AdRequest.Builder().build());
                }
            }
        });
    }


    private void displayAd() {
        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }
    }


 public class task extends AsyncTask<Void,Void,Void>{


     @Override
     protected Void doInBackground(Void... params) {
         try {
             Document document= Jsoup.connect("http://www.kuexam.ac.in/schemes/").get();
             Element element= document.select("div[class=sitemap]").first();
             Elements elements= element.select("a");

             for (int i=1;i<=30;i++) {

                 myData = new data("http://www.kuexam.ac.in" + elements.get(i).attr("href"), elements.get(i).text());

                 if (elements.get(i).text().contains("2017")) {
                     arrayList.add(myData);
                 }
                 if (i==20){
                     progressDialog.dismiss();
                 }
             }


         } catch (IOException e) {
             e.printStackTrace();
         }


         return null;
     }

     @Override
     protected void onPostExecute(Void aVoid) {
         super.onPostExecute(aVoid);
         RecyclerView.Adapter adapter=new adeptor(arrayList,getApplicationContext());
         recyclerView.setAdapter(adapter);
         RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
         recyclerView.setLayoutManager(layoutManager);
     }
 }

}
