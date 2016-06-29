package com.ejemplo.album.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.ejemplo.album.model.Album;
import com.ejemplo.album.model.AlbumContainer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import util.DAOException;
import util.HTTPConnectionManager;
import util.ResultListener;

/**
 * Created by digitalhouse on 27/06/16.
 */
public class AlbumDAO extends SQLiteOpenHelper {

    private static final String DATABASENAME = "AlbumDB";
    private static final Integer DATABASEVERSION = 1;

    private static final String TABLEALBUM = "Albums";
    private static final String IDKEY = "Id";
    private static final String ALBUMID = "AlbumID";
    private static final String TITLE ="Title";
    private static final String URL = "Url";
    private static final String THUMBNAILURL = "ThumbnailUrl";

    private Context context;
    AlbumContainer albumContainer;

    public AlbumDAO (Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLEALBUM + "("
                + IDKEY + " TEXT PRIMARY KEY, "
                + ALBUMID + " TEXT, "
                + TITLE + " TEXT, "
                + URL + " TEXT, "
                + THUMBNAILURL + " TEXT "
                + ")";

        db.execSQL(createTable);

    }

    public void addAlbum (Album album){

        if(!checkIfIDExists(album.getId())) {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues row = new ContentValues();

            row.put(IDKEY, album.getId());
            row.put(ALBUMID, album.getAlbumId());
            row.put(THUMBNAILURL, album.getThumbnailUrl());
            row.put(TITLE, album.getTitle());
            row.put(URL, album.getUrl());

            db.insert(TABLEALBUM, null, row);

            db.close();
        } else {
            Log.v("addAlbum error: ", "La id " + album.getId() + "ya existe en la base. No se agrega.");
        }
    }

    public void addAlbumList(List<Album> albumList){
        for (Album actualAlbum : albumList) {
            if (!checkIfIDExists(actualAlbum.getId())){
                this.addAlbum(actualAlbum);
            }
        }
    }

    private Boolean checkIfIDExists (String itemID){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLEALBUM
                + " WHERE " + IDKEY + "==" + itemID;

        Cursor result = db.rawQuery(selectQuery, null);
        Integer count = result.getCount();

        db.close();

        return (count > 0);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void getAlbum (ResultListener<List<Album>> listener, Context context){
        RetrieveFeedTask retrieveFeedTask = new RetrieveFeedTask(listener);
        retrieveFeedTask.execute();
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, List<Album>> {

        private ResultListener<List<Album>> listener;

        public RetrieveFeedTask (ResultListener<List<Album>> listener){
            this.listener = listener;
        }

        @Override
        protected List<Album> doInBackground(String... params) {
            HTTPConnectionManager connectionManager = new HTTPConnectionManager();
            String input = null;

            try {
                input = connectionManager.getRequestString("https://api.myjson.com/bins/25Hip");
            }
            catch (DAOException e){
                e.printStackTrace();
            }

            //List<Album> result = new ArrayList<>();
            Gson gson = new Gson();
            albumContainer = gson.fromJson(input, AlbumContainer.class);

            return albumContainer.getAlbums();
        }

        @Override
        protected void onPostExecute(List<Album> albums) {

            addAlbumList(albumContainer.getAlbums());
            this.listener.finish(albums);
        }
    }

    public List<Album> getAllAlbumsFromDB () {
        List<Album> albumsList = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();

        String selectQuery = "SELECT * FROM "+ TABLEALBUM;
        Cursor cursor = database.rawQuery(selectQuery,null);

        while (cursor.moveToNext()){
            Album album = new Album();

            album.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            album.setAlbumId(cursor.getString(cursor.getColumnIndex(ALBUMID)));
            album.setId(cursor.getString(cursor.getColumnIndex(IDKEY)));
            album.setThumbnailUrl(cursor.getString(cursor.getColumnIndex(THUMBNAILURL)));
            album.setUrl(cursor.getString(cursor.getColumnIndex(URL)));

            albumsList.add(album);
        }
        return albumsList;
    }

}
