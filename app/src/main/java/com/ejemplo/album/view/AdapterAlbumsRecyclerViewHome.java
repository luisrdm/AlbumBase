package com.ejemplo.album.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ejemplo.album.R;
import com.ejemplo.album.model.Album;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Luis R. Díaz Muñiz 28/jun/16
 */
public class AdapterAlbumsRecyclerViewHome extends RecyclerView.Adapter implements View.OnClickListener {

    private View.OnClickListener listener;
    private List<Album> albumsList;
    private Context context;


    public AdapterAlbumsRecyclerViewHome(List<Album> albumsList, Context context) {
        this.albumsList = albumsList;
        this.context = context;
    }

    public void setOnClickListener(View.OnClickListener unListener){
        listener = unListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_detail_home, parent, false);

        AlbumsViewHolder albumsViewHolder = new AlbumsViewHolder(itemView);

        itemView.setOnClickListener(this);

        return  albumsViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Album anAlbum = albumsList.get(position);

        AlbumsViewHolder albumsViewHolder = (AlbumsViewHolder) holder;

        albumsViewHolder.bindPelicula(anAlbum, context);
    }

    @Override
    public int getItemCount() {
        return albumsList.size();
    }


    @Override
    public void onClick(View view) {
        listener.onClick(view);
    }

    private static class AlbumsViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewAlbumTitle;
        private ImageView imageViewAlbumThumb;

        public AlbumsViewHolder(View itemView) {
            super(itemView);

            textViewAlbumTitle = (TextView) itemView.findViewById(R.id.textViewAlbumTitle);
            imageViewAlbumThumb = (ImageView) itemView.findViewById(R.id.imageViewAlbumThumb);
        }

        public void bindPelicula(Album anAlbum, Context context) {

            textViewAlbumTitle.setText(anAlbum.getTitle());

            Picasso.with(context).load(anAlbum.getThumbnailUrl()).into(imageViewAlbumThumb);
        }
    }
}
















