package com.ejemplo.album.controller;

import android.content.Context;

import com.ejemplo.album.dao.AlbumDAO;
import com.ejemplo.album.model.Album;

import java.util.List;

import util.ResultListener;

/**
 * Created by digitalhouse on 27/06/16.
 */
public class AlbumController {

    public void getAlbums (final ResultListener<List<Album>> listener, Context context) {
        AlbumDAO albumDAO = new AlbumDAO(context);

        albumDAO.getAlbum(new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                listener.finish(resultado);
            }
        },context);
    }

}
