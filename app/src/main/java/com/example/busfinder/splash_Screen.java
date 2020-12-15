package com.example.busfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash_Screen extends AppCompatActivity {

    private static int SPLASH_SCREEN=4000;
    //Variables
    Animation topAnim,bottomAnim;
    ImageView imageview;
    TextView logo1,slogan1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash__screen);

        //animation
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        imageview=findViewById(R.id.image);
        logo1=findViewById(R.id.logo);
        slogan1=findViewById(R.id.slogan);

        //assigning animation

        imageview.setAnimation(topAnim);
        logo1.setAnimation(bottomAnim);
        slogan1.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(splash_Screen.this,login.class);
                Pair[] pairs=new Pair[2];

                pairs[0]=new Pair<View,String>(imageview,"image_logo");
                pairs[1]=new Pair<View,String>(logo1,"logo_text");

                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(splash_Screen.this,pairs);
                startActivity(intent,options.toBundle());
                finish();


            }
        },SPLASH_SCREEN);
    }
}