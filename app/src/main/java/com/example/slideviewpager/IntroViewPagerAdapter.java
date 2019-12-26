package com.example.slideviewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.LinkedList;
import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context context;
    List<ScreenItem> screenItemList;

    public IntroViewPagerAdapter(Context context, List<ScreenItem> screenItemList) {
        this.context = context;
        this.screenItemList = screenItemList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_screen, null);

        ImageView imgSlide = view.findViewById(R.id.intro_img);
        TextView title = view.findViewById(R.id.intro_title);
        TextView description = view.findViewById(R.id.intro_description);

        title.setText(screenItemList.get(position).getTitle());
        description.setText(screenItemList.get(position).getDescription());
        imgSlide.setImageResource(screenItemList.get(position).getScreenImg());

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return screenItemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
