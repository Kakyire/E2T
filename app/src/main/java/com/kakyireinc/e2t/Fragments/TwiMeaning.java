package com.kakyireinc.e2t.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kakyireinc.e2t.Database.KakyireDB;
import com.kakyireinc.e2t.R;

import java.util.Locale;

/**
 * Created by Kakyire LastBorn on 3/2/2019.
 */

public class TwiMeaning extends AppCompatActivity {


    TextView textView;
    Intent intent;
    Typeface typeface;
    KakyireDB kakyireDB;
    String meaning;
    TextToSpeech speech = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twi_fragment);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initializing views and objects
        kakyireDB = new KakyireDB(getApplicationContext());
        textView = (TextView)findViewById(R.id.twi_meaning);
        typeface = Typeface.createFromAsset(getAssets(), "fonttwi.TTF");


        intent = getIntent();
        meaning = intent.getStringExtra("meaning");
        loadMeaning();

        textView.setTypeface(typeface);

        speech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status!=TextToSpeech.ERROR){
                    speech.setLanguage(Locale.UK);
                }
            }
        });

    }

    private void loadMeaning() {

        Cursor cursor = kakyireDB.Selection("select meaning from twi where english ='" + meaning+"'");
        int mMeaning = cursor.getColumnIndex("meaning");

        if (cursor.moveToFirst()) {
            textView.setText(Html.fromHtml(cursor.getString(mMeaning)));
            Log.i("Meaning",String.valueOf(cursor.getString(mMeaning)+" "+meaning));
        }
    }


   private void speakMeaning(){
    speech.speak(meaning,TextToSpeech.QUEUE_FLUSH,null);

   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meaning_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


       int id=item.getItemId();
       if (id==R.id.play_word){
           speakMeaning();

       }

       return true;
    }
}
