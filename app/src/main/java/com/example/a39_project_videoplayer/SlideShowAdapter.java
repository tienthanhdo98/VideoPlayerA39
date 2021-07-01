package com.example.a39_project_videoplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.squareup.picasso.Picasso;
import java.util.List;

public class SlideShowAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;
    List<String> listUrlImg;

    public SlideShowAdapter(Context context, List<String> listUrlImg) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listUrlImg = listUrlImg;
    }

    @Override
    public int getCount() {
        if(listUrlImg != null){
            return listUrlImg.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View itemView = inflater.inflate(R.layout.layout_slideshow, container, false);

        ImageView imageView = itemView.findViewById(R.id.imgSlideShow);

        Picasso.get().load(listUrlImg.get(position))
                .fit()
                .into(imageView);

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
