package FragmentHot;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a39_project_videoplayer.R;
import com.example.a39_project_videoplayer.VideoDescription;
import com.example.a39_project_videoplayer.iClickItem;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    List<VideoDescription> list;
    com.example.a39_project_videoplayer.iClickItem iClickItem;


    public VideoAdapter(List<VideoDescription> list) {
        this.list = list;
    }

    public void setiClickItem(iClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_listvideo_hot, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final VideoDescription videoDescription = list.get(position);
        Picasso.get().load(videoDescription.getAvatar()).into(holder.imgAvt);

        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //iClickItem.onClickTitle(videoDescription.getFileMp4(),videoDescription.getTitle(),videoDescription.getDatePublished());
            }
        });
        holder.tvTitle.setText(videoDescription.getTitle());


        //date
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String dateGetFromUrl = videoDescription.getDatePublished();
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = formatter1.parse(dateGetFromUrl);

            String dateString = df.format(date1);
            holder.tvPulishedDay.setText(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPulishedDay;
        ImageView imgAvt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTile);
            tvPulishedDay = itemView.findViewById(R.id.tvPublishDay);
            imgAvt = itemView.findViewById(R.id.imgAVT);
        }
    }
}
