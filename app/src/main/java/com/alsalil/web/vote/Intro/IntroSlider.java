package com.alsalil.web.vote.Intro;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alsalil.web.vote.MemberShip;
import com.alsalil.web.vote.R;

public class IntroSlider extends AppCompatActivity implements View.OnClickListener{

    private ViewPager viewPager ;
    private int []layouts = {R.layout.slide1, R.layout.slide2, R.layout.slide3};
    private IntroAdapter adapter ;
    private LinearLayout dots_layout ;
    private ImageView[]imageView ;
    private Button skip, next ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (new PreferenceManager(this).check_preferences()){
            loadHome();
        }
        if (Build.VERSION.SDK_INT >= 19){

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_intro);



        // start initialize views of your project ....
        skip = (Button) findViewById(R.id.skip_btn);
        next = (Button) findViewById(R.id.next_btn);

        // view Pager ....
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new IntroAdapter(this, layouts);
        viewPager.setAdapter(adapter);

        skip.setOnClickListener(this);
        next.setOnClickListener(this);

        dots_layout = (LinearLayout) findViewById(R.id.dots_layout);
        createDots(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                createDots(position);
                if (position == layouts.length -1){
                    next.setText("START");
                    skip.setVisibility(View.INVISIBLE);
                } else {

                    next.setText("NEXT");
                    skip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void createDots(int currentPosition){

        if (dots_layout != null)
            dots_layout.removeAllViews();

        imageView = new ImageView[layouts.length];
        for (int i=0; i<layouts.length ; i++){
            imageView[i] = new ImageView(this);

            if (i == currentPosition){
                imageView[i].setImageDrawable(ContextCompat.getDrawable(this , R.drawable.active_dots));
            } else {
                imageView[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive_dots));
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(4, 0,4 ,0);

            dots_layout.addView(imageView[i] , layoutParams);
        }
    }


    @Override
    public void onClick(View view) {

        int view_id = view.getId();
        switch (view_id){
            case R.id.next_btn:

                loadNext();
                break;

            case R.id.skip_btn:
                loadHome();
                new PreferenceManager(this).writePreferences();
                break;
        }
    }


    public void loadHome(){
        startActivity(new Intent(IntroSlider.this, MemberShip.class));
        finish();
    }

    public void loadNext(){

        int next_slide = viewPager.getCurrentItem()+1 ;

        if (next_slide < layouts.length ){
            viewPager.setCurrentItem(next_slide);
        } else {

            loadHome();
            new PreferenceManager(this).writePreferences();
        }

    }

}
