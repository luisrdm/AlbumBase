package com.ejemplo.album.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ejemplo.album.R;
import com.ejemplo.album.controller.AlbumController;
import com.ejemplo.album.model.Album;

import java.util.ArrayList;
import java.util.List;

import util.ResultListener;

public class MainActivity extends AppCompatActivity {

    //Esta variable es una manera muy conveniente de guardarse siempre un contexto. Aca como estamos en un activity, el mismo va a ser el contexto.
    Context context;

    List<Album> albumsList = new ArrayList<>();
    RecyclerView recyclerViewAlbums;
    AdapterAlbumsRecyclerViewHome adapterAlbumsRecyclerViewHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        AlbumController albumController = new AlbumController();

        albumController.getAlbums(new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                albumsList = resultado;
                recyclerViewAlbums = (RecyclerView) findViewById(R.id.recyclerViewHome);

                adapterAlbumsRecyclerViewHome = new AdapterAlbumsRecyclerViewHome(albumsList, context);
                recyclerViewAlbums.setAdapter(adapterAlbumsRecyclerViewHome);

                recyclerViewAlbums.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));

                recyclerViewAlbums.setHasFixedSize(true);

                ListenerAlbums listenerAlbums = new ListenerAlbums();
                adapterAlbumsRecyclerViewHome.setOnClickListener(listenerAlbums);

                Log.v("Pruebaaaaaaaaaaaaa", albumsList.toString());
            }
        }, context);

    }

    private class ListenerAlbums implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "Se presionó el item " + recyclerViewAlbums.getChildPosition(v), Toast.LENGTH_SHORT).show();
        }
    }

}











