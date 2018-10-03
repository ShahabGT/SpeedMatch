package ir.shahabazimi.speedmatch;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    ImageView logo;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        initViews();
        animateViews();
    }
    private void initViews(){
        text = findViewById(R.id.splash_text);
        logo = findViewById(R.id.splash_logo);
    }


    private void animateViews(){
        ObjectAnimator alpha = new ObjectAnimator().ofFloat(
                logo,
                "alpha",
                0.0f,1f
        );
        alpha.setDuration(2000);
        ObjectAnimator scalex = new ObjectAnimator().ofFloat(
                logo,
                "scaleX",
                0f,1f
        );
        scalex.setDuration(2000);
        ObjectAnimator scaley = new ObjectAnimator().ofFloat(
                logo,
                "scaleY",
                0f,1f
        );
        scaley.setDuration(2000);
        ObjectAnimator translationx = new ObjectAnimator().ofFloat(
                text,
                "translationX",
                -7000f,0
        );
        translationx.setDuration(750);
        ObjectAnimator translationx2 = new ObjectAnimator().ofFloat(
                text,
                "translationX",
                0,7000f
        );
        translationx2.setStartDelay(1650);
        translationx2.setDuration(750);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alpha,scalex,scaley,translationx,translationx2);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        animatorSet.start();



    }
}
