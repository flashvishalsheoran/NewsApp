package devops.vishal.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private AnimationDrawable wifiAnimation;
    private  ImageView imageView;
    private int SPLASH_DISPLAY_LENGTH = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.splashTheme);
        setContentView(R.layout.activity_splash);

        //Loading Animation for Splash Screen
        imageView = (ImageView) findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.animation);
        wifiAnimation = (AnimationDrawable) imageView.getBackground();

        //Splash Screen for the App
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        wifiAnimation.start();
    }
}
