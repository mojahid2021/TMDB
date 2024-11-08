package com.horizonhunters.tmdb.home.Movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.horizonhunters.tmdb.R;

import java.util.List;

public class ContentAdapter3 extends RecyclerView.Adapter<ContentAdapter3.ContentViewHolder3> {
    private List<Content3> content3List;
    private Context context;

    public ContentAdapter3(List<Content3> content3List, Context context) {
        this.content3List = content3List;
        this.context = context;
    }

    @NonNull
    @Override
    public ContentAdapter3.ContentViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster, parent, false);
        return new ContentViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentAdapter3.ContentViewHolder3 holder, int position) {
        Content3 content3 = content3List.get(position);
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + content3.getPosterPath()) // Base URL for TMDB images
                // Placeholder image
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return content3List.size();
    }

    public class ContentViewHolder3 extends RecyclerView.ViewHolder {
        ImageView poster;
        public ContentViewHolder3(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
        }
    }
}
