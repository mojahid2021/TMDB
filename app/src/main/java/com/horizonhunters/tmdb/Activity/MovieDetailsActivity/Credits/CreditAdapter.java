package com.horizonhunters.tmdb.Activity.MovieDetailsActivity.Credits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.horizonhunters.tmdb.R;

import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.ViewHolder> {

    private List<Credit> credits;
    private Context context;

    public CreditAdapter(List<Credit> credits, Context context) {
        this.credits = credits;
        this.context = context;
    }

    @NonNull
    @Override
    public CreditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_credit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditAdapter.ViewHolder holder, int position) {
        Credit credit = credits.get(position);
        holder.name.setText(credit.getName()); // Changed mame to name
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + credit.getProfilePath()).into(holder.profile);
    }

    @Override
    public int getItemCount() {
        return credits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profile;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.profile);
            name = itemView.findViewById(R.id.name); // Changed mame to name
        }
    }
}
