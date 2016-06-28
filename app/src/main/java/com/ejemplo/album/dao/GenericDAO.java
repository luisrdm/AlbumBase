package com.ejemplo.album.dao;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by andres on 07/06/16.
 */
public class GenericDAO extends SQLiteOpenHelper {

    public GenericDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Object getObject(Context context, Class aClass, String fileName){

        Object object = null;
        try{

            AssetManager manager = context.getAssets();
            InputStream inputStream = manager.open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            Gson gson = new Gson();
            object = gson.fromJson(bufferedReader, aClass);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return object;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
