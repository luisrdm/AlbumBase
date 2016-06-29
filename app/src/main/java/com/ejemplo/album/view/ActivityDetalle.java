package com.ejemplo.album.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ejemplo.album.R;
import com.ejemplo.album.controller.AlbumController;
import com.ejemplo.album.model.Album;
import com.squareup.picasso.Picasso;

import java.util.List;

import util.ResultListener;

/**
 * Created by digitalhouse on 28/06/16.
 */
public class ActivityDetalle extends AppCompatActivity {

    public static final String ALBUMPOSITION = "albumPosition";

    Integer albumPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        albumPosition = bundle.getInt(ALBUMPOSITION);

        AlbumController albumController = new AlbumController();

        albumController.getAlbums(new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                showSelectedAlbum(resultado);
            }
        }, this);


    }

    private void showSelectedAlbum(List<Album> resultado){
        TextView textViewAlbumTitle = (TextView) findViewById(R.id.activity_detalle_textView_albumTitle);
        ImageView imageViewAlbumBigImage = (ImageView) findViewById(R.id.activity_detalle_ImageView_albumBigImage);
        TextView textViewAlbumID = (TextView) findViewById(R.id.activity_detalle_textViewAlbumID);

        Album actualAlbum = new Album();
        actualAlbum = resultado.get(albumPosition);

        textViewAlbumTitle.setText(actualAlbum.getTitle());
        textViewAlbumID.setText(actualAlbum.getId());

        Picasso.with(this).load(actualAlbum.getUrl()).placeholder(R.drawable.icon_cd).error(R.drawable.icon_cd).into(imageViewAlbumBigImage);

    }
}
