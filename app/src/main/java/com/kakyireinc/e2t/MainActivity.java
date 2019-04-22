package com.kakyireinc.e2t;

import android.content.Intent;
import android.database.Cursor;
//import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.kakyireinc.e2t.Database.KakyireDB;
import com.kakyireinc.e2t.Fragments.English;
import com.kakyireinc.e2t.Fragments.TwiMeaning;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    KakyireDB myDataBase;
    Cursor cursor;
    English english = new English();
    TwiMeaning twiMeaning = new TwiMeaning();
    ArrayList<String> word = new ArrayList<>();
    MaterialSearchView materialSearchView;
    AdapterClass adapterClass;


    ListView listView;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //initializing views and objects
        listView = (ListView) findViewById(R.id.word_list);
        myDataBase = new KakyireDB(getApplicationContext());
        materialSearchView= (MaterialSearchView) findViewById(R.id.materialsearchview);


        try {
            myDataBase.NewDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myDataBase.OpenDb();


        PopulateList();
        adapterClass=new AdapterClass(getApplicationContext(),word);
//        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, word);
//        arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.list_item, R.id.text1, word);
        listView.setAdapter(adapterClass);








        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText != null && !newText.isEmpty()) {
                    String sql = "select  * from twi where english like '" + newText + "%'";
//                    myDataBase.getWritableDatabase();
                    cursor=myDataBase.Selection(sql);
                    int english = cursor.getColumnIndex("english");


                    if (cursor.moveToFirst()) {
                        word.clear();


                        do {

                            word.add(cursor.getString(english));


                        }
                        while (cursor.moveToNext());

//                arrayAdapter.notifyDataSetChanged();
                        adapterClass=new AdapterClass(getApplicationContext(),word);
//                        arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,word);
                        listView.setAdapter(adapterClass);
                        myDataBase.close();
                    }

                } else {
                    PopulateList();
                }
                return true;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                PopulateList();

            }
        });





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getApplicationContext(), TwiMeaning.class);
                intent.putExtra("meaning", String.valueOf(word.get(position)));
                startActivity(intent);

            }
        });


    }


    //creating item


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        materialSearchView.setMenuItem(menuItem);


        return true;
    }

    //populating the list view
    public void PopulateList() {
//        MyDataBase myDataBase;
        try {
            myDataBase = new KakyireDB(getApplicationContext());
//            myDataBase.getWritableDatabase();


            cursor = myDataBase.Selection("select * from twi order by _id asc");

            int english = cursor.getColumnIndex("english");


            if (cursor.moveToFirst()) {
                word.clear();


                do {

                    word.add(cursor.getString(english));


                }
                while (cursor.moveToNext());

//                arrayAdapter.notifyDataSetChanged();
                myDataBase.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //changing fragment
    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.info) {
            Toast.makeText(this, Html.fromHtml("This App is developed by <b>Kakyire Inc.</b>"), Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}
