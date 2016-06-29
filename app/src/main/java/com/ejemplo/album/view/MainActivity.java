package com.ejemplo.album.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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

    Context context;

    List<Album> albumsList = new ArrayList<>();
    RecyclerView recyclerViewAlbums;
    AdapterAlbumsRecyclerViewHome adapterAlbumsRecyclerViewHome;
    AlbumController albumController;
    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        albumController = new AlbumController();

        askControllerForAlbums();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.activity_main_SwipeRefresh);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                askControllerForAlbums();
            }
        });

    }

    private void askControllerForAlbums (){
        albumController.getAlbums(new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                swipeContainer.setRefreshing(true);

                albumsList = resultado;
                recyclerViewAlbums = (RecyclerView) findViewById(R.id.recyclerViewHome);

                adapterAlbumsRecyclerViewHome = new AdapterAlbumsRecyclerViewHome(albumsList, context);
                recyclerViewAlbums.setAdapter(adapterAlbumsRecyclerViewHome);

                recyclerViewAlbums.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));

                recyclerViewAlbums.setHasFixedSize(true);

                ListenerAlbums listenerAlbums = new ListenerAlbums();
                adapterAlbumsRecyclerViewHome.setOnClickListener(listenerAlbums);
                swipeContainer.setRefreshing(false);

            }
        }, context);
    }

    private class ListenerAlbums implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Integer albumPosition = recyclerViewAlbums.getChildPosition(v);

            Intent intent = new Intent(context, ActivityDetalle.class);

            Bundle bundle = new Bundle();

            bundle.putInt(ActivityDetalle.ALBUMPOSITION, albumPosition);

            intent.putExtras(bundle);

            startActivity(intent);

        }
    }

}











