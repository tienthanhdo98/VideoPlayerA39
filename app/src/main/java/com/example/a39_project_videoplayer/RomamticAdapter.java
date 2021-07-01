package com.example.a39_project_videoplayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RomamticAdapter extends RecyclerView.Adapter<RomamticAdapter.ViewHolder> {

    List<VideoDescription> list;
    iClickItem iClickItem;

    public RomamticAdapter(List<VideoDescription> list) {
        this.list = list;
    }

    public void setiClickItem(com.example.a39_project_videoplayer.iClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_hoziron,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final VideoDescription videoDescription = list.get(position);
        Picasso.get().load(videoDescription.getAvatar()).into(holder.imgAvt);
        holder.tvTitle.setText(videoDescription.getTitle());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItem.onClickItem(videoDescription);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvt;
        TextView tvTitle;
        LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvt = itemView.findViewById(R.id.img_list_home);
            tvTitle = itemView.findViewById(R.id.tv_name_home);
            item = itemView.findViewById(R.id.itemFilm);
        }
    }
}
