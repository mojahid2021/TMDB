package com.horizonhunters.tmdb.genres;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.horizonhunters.tmdb.R;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenreViewHolder> {

    private List<String> genreList;

    public GenresAdapter(List<String> genreList) {
        this.genreList = genreList;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_item, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        holder.genreName.setText(genreList.get(position));
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder {
        TextView genreName;

        public GenreViewHolder(View itemView) {
            super(itemView);
            genreName = itemView.findViewById(R.id.genreName);
        }
    }
}
