package com.kakyireinc.e2t.Database;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Kakyire LastBorn on 1/7/2019.
 */

public class KakyireDB extends SQLiteOpenHelper {

    private static String DB_NAME = "e2t";
    private static String DIR = "data/data/com.kakyireinc.e2t/databases/";
    private static String DB_PATH = DIR + DB_NAME;
    private static int VERSION = 1;
    private static String shareKey = "DataVersion";
    int newVer;
    ProgressDialog dialog;
    SharedPreferences sharedPreferences;


    private static Context context;
    private static SQLiteDatabase sqLiteDatabase = null;

    public KakyireDB(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;

        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
//        this.VERSION = dbversion;
    }


    //checking for the existence of database
    private boolean Check() {




        if (context.getDatabasePath(DB_NAME).exists()) {


            try {
                sqLiteDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);


//          OpenDb();

            } catch (SQLException e) {
                Log.i("kakyire Check", "Checking of DB Error");
//            Toast.makeText(context, "Checking of DB Error", Toast.LENGTH_SHORT).show();
            }
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }

        }


        return this.sqLiteDatabase != null ? true : false;

    }


    //copy database from file
    private void Copy() throws IOException {



        dialog = new ProgressDialog(context);
        dialog.setTitle("Installing new Database");
        dialog.setMessage("Please wait...");
//        dialog.show();


        sharedPreferences.edit().putInt(shareKey, VERSION).apply();

        InputStream inputStream = context.getAssets().open("e2t.db");
        OutputStream outputStream = new FileOutputStream(DB_PATH);


        byte[] buffer = new byte[1024];

        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);

//            Toast.makeText(context, "Creating new Database please wait...", Toast.LENGTH_SHORT).show();

            Log.i("kakyire create", "Creating new database");
        }



        //closing the stream
        outputStream.flush();
        outputStream.close();
        inputStream.close();


    }


    //creating database
    public void NewDB() throws IOException {


        boolean dbCheck = Check();


        if (dbCheck) {
            OpenDb();
            //if database is already installed skip here or install new one if it is update

            newVer = sharedPreferences.getInt(shareKey, 0);
            if (VERSION > newVer) {
                sharedPreferences.edit().putInt(shareKey, VERSION).apply();
                Log.i("Kakyire update", "Update can be installed");
                Copy();
                dialog.dismiss();


            }
            Log.i("kakyire exist", "Db already exist " + newVer);

        } else {

            //if database is not installed already then install
            this.getReadableDatabase();
            try {
                Copy();
                dialog.dismiss();
//                Toast.makeText(context, "Database finished Installing", Toast.LENGTH_SHORT).show();

                Log.i("kakyire finish", "Database finished Installing");
            } catch (Exception e) {
                Log.i("DB copy", "There is error installing database");
//                Toast.makeText(context, "There is error installing database", Toast.LENGTH_SHORT).show();
            }
        }


        Log.i("Kakyire DBVersin", String.valueOf(newVer));

    }


    //opening the database for use
    public void OpenDb() {

        sqLiteDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
    }


    //selection of items in the database
    public Cursor Selection(String sql) {
        sqLiteDatabase = getWritableDatabase();
        return this.sqLiteDatabase.rawQuery(sql, null);
    }

    public synchronized void close() {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
            super.close();
        }
    }


    //adding hymn to methodist favourites
    public void popmethodistFav(int number, String title) {
        sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("number", Integer.valueOf(number));
        values.put("title", title);
        sqLiteDatabase.insert("methodistfav", null, values);
        sqLiteDatabase.close();


    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }







    //loading database in the background



}
