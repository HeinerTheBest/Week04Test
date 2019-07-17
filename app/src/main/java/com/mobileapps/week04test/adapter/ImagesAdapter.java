package com.mobileapps.week04test.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapps.week04test.R;
import com.mobileapps.week04test.activities.FullScreenActivity;
import com.mobileapps.week04test.models.PicturesResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder>
{
    List<PicturesResponse> images;
    Context context;

    public ImagesAdapter(List<PicturesResponse> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.image_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position)
    {
        final String urlPicture = images.get(position).getThumbnailUrl();
        Picasso.with(context)
                .load(urlPicture)
                .into(holder.imgPicture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Heiner","Adapter is "+images.get(position).isFavorite()+" with id "+images.get(position).getId());
                Intent intent = new Intent(context, FullScreenActivity.class);
                intent.putExtra("id_picture",images.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgPicture;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPicture = itemView.findViewById(R.id.imgPicture);
        }
    }
}