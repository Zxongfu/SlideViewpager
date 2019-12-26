package com.example.slideviewpager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
    private ViewPager viewPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabLayout;
    Button btn_next, btn_getstarted;
    int position;
    Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // when this activity is about to be launch we need to check if its openned before or not
        if (restorePrefData()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_intro);

        //init
        btn_next = findViewById(R.id.btn_next);
        btn_getstarted = findViewById(R.id.btn_getstarted);
        tabLayout = findViewById(R.id.indication);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animated);


        getSupportActionBar().hide();

        //fill screen list
        final List<ScreenItem> screenItemList = new ArrayList<>();
        screenItemList.add(new ScreenItem("FreshFood", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.", R.drawable.img1));
        screenItemList.add(new ScreenItem("Fast Delivery", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.", R.drawable.img2));
        screenItemList.add(new ScreenItem("Easy Payment", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.", R.drawable.img3));

        //setup viewpager
        viewPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, screenItemList);
        viewPager.setAdapter(introViewPagerAdapter);

        //setup tableayout
        tabLayout.setupWithViewPager(viewPager);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = viewPager.getCurrentItem();
                if (position < screenItemList.size()) {
                    position++;
                    viewPager.setCurrentItem(position);
                }

                //when reach to last screen
                if (position == screenItemList.size() - 1) {
                    // TODO: 2019/12/26 show getStarted button and hide indicator
                    loadLastScreen();
                }
            }
        });

        //tablayout setup with viewpager
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == screenItemList.size() - 1) {
                    loadLastScreen();
                } else {

                    btn_next.setVisibility(View.VISIBLE);
                    btn_getstarted.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btn_getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                savePrefsData();
                finish();
            }
        });
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend", true);
        editor.commit();
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend", false);
        return isIntroActivityOpnendBefore;

    }

    private void loadLastScreen() {
        btn_next.setVisibility(View.GONE);
        btn_getstarted.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.GONE);
        // TODO: 2019/12/26  add an animation the get started button
        btn_getstarted.setAnimation(animation);
    }
}
