package com.ejemplo.album.controller;

import android.content.Context;
import android.widget.Toast;

import com.ejemplo.album.dao.AlbumDAO;
import com.ejemplo.album.model.Album;

import java.util.List;

import util.HTTPConnectionManager;
import util.ResultListener;

/**
 * Created by digitalhouse on 27/06/16.
 */
public class AlbumController {

    public void getAlbums (final ResultListener<List<Album>> listener, Context context) {
        AlbumDAO albumDAO = new AlbumDAO(context);

        if(HTTPConnectionManager.isNetworkingOnline(context)) {
            albumDAO.getAlbum(new ResultListener<List<Album>>() {
                @Override
                public void finish(List<Album> resultado) {
                    listener.finish(resultado);
                }
            }, context);
        } else {
            List<Album> albumLists = albumDAO.getAllAlbumsFromDB();

            listener.finish(albumLists);

            Toast.makeText(context, "No Internet > offline Data.", Toast.LENGTH_SHORT).show();
        }
    }

}
