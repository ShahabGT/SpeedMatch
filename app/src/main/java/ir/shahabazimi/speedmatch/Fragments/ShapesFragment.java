package ir.shahabazimi.speedmatch.Fragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import ir.shahabazimi.speedmatch.Dialogs.ScoreDialog;
import ir.shahabazimi.speedmatch.R;


public class ShapesFragment extends Fragment {

    private TextView animText,timer,score;
    private Button zero,one,two;
    private RelativeLayout gamelayout;
    private LinearLayout shapeControl;
    private ImageView answerImage;
    private ImageView shape;

    private CountDownTimer countDownTimer;

    private boolean answer=false;
    private int count=3;
    private int[] colors={Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW};
    private int[] shapes={R.drawable.rectangle,R.drawable.oval,R.drawable.triangle};
    private int currentColorIndex=0,currentShapeIndex=0, previousColorIndex=0,previousShapeIndex=0;
    private long gameTime = 60000;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_shapes, container, false);
        init(v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startGameAnimation();

    }

    private void init(View v){
        animText = v.findViewById(R.id.shapes_startanim);
        score = v.findViewById(R.id.shapes_score);
        timer = v.findViewById(R.id.shapes_timer);


        zero=v.findViewById(R.id.shapes_zerobtn);
        one=v.findViewById(R.id.shapes_onebtn);
        two=v.findViewById(R.id.shapes_twobtn);

        answerImage = v.findViewById(R.id.shapes_answer);
        shape = v.findViewById(R.id.shapes_shape);

        gamelayout= v.findViewById(R.id.shapes_game_layout);
        shapeControl= v.findViewById(R.id.shapes_controls);




    }

    private void startGame(){

        randomShape();

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((currentColorIndex==previousColorIndex)&&(currentShapeIndex==previousShapeIndex)){
                    int res = Integer.parseInt(score.getText().toString())+1;
                    score.setText(res+"");
                    answer =true;
                }
                answerAnimation(answer);
                shapeExitAnimation();
                answer =false;

            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((currentColorIndex==previousColorIndex)&&(currentShapeIndex!=previousShapeIndex))
                        ||((currentColorIndex!=previousColorIndex)&&(currentShapeIndex==previousShapeIndex)) ){
                    int res = Integer.parseInt(score.getText().toString())+1;
                    score.setText(res+"");
                    answer =true;
                }
                answerAnimation(answer);
                shapeExitAnimation();
                answer =false;

            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((currentColorIndex!=previousColorIndex)&&(currentShapeIndex!=previousShapeIndex)){
                    int res = Integer.parseInt(score.getText().toString())+1;
                    score.setText(res+"");
                    answer =true;
                }
                answerAnimation(answer);
                shapeExitAnimation();
                answer =false;

            }
        });


    }

    private void initGame(){
        Random random = new Random();
        currentColorIndex =random.nextInt(4);
        currentShapeIndex =random.nextInt(3);

        shape.setBackground(getResources().getDrawable(shapes[currentShapeIndex]));
        setShapeColor(colors[currentColorIndex]);
    }
    private void randomShape(){
        previousShapeIndex = currentShapeIndex;
        previousColorIndex = currentColorIndex;

        Random random = new Random();
        currentColorIndex =random.nextInt(4);
        currentShapeIndex =random.nextInt(3);


        shape.setBackground(getResources().getDrawable(shapes[currentShapeIndex]));
        setShapeColor(colors[currentColorIndex]);
    }
    private void setShapeColor(int color){
        Drawable background = shape.getBackground();
        if (background instanceof ShapeDrawable) {
            ShapeDrawable shapeDrawable = (ShapeDrawable) background;
            shapeDrawable.getPaint().setColor(color);
        } else if (background instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) background;
            gradientDrawable.setColor(color);
        } else if (background instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) background;
            colorDrawable.setColor(color);
        }
    }
    private void startGameAnimation(){
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(
                animText,
                "ScaleX",
                0f,2.5f
        );
        scaleXAnim.setDuration(1700);

        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(
                animText,
                "ScaleY",
                0f,2.5f
        );
        scaleYAnim.setDuration(1700);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(
                animText,
                "alpha",
                1f,0f
        );
        alphaAnim.setDuration(1700);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnim,scaleYAnim,alphaAnim);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(count>0) {
                    startGameAnimation();
                }
                else if(count==0){
                    gamelayout.setVisibility(View.VISIBLE);
                    animText.setVisibility(View.GONE);
                    initGame();
                    startGameAnimation();

                }
                else {
                    shapeControl.setVisibility(View.VISIBLE);
                    shapeExitAnimation();
                    startTimer(gameTime);
                }

            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

                switch (count){
                    case 1: animText.setText(getString(R.string.anim_txt1)); break;
                    case 2: animText.setText(getString(R.string.anim_txt2)); break;
                    case 3: animText.setText(getString(R.string.anim_txt3)); break;
                }
                count--;

            }
        });
        animatorSet.start();


    }
    private void startTimer(long time){
        countDownTimer = new CountDownTimer(time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(convertToTimeFormat(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                ScoreDialog scoreDialog = new ScoreDialog(getActivity(),getArguments().getString("username"));
                scoreDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                scoreDialog.score=score.getText().toString();
                scoreDialog.game="shapes";
                scoreDialog.show();

            }
        };
        countDownTimer.start();
    }
    private String convertToTimeFormat(long millisecond){
        Date date = new Date(millisecond);
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }
    private void answerAnimation(final boolean answer){
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(
                answerImage,
                "ScaleX",
                1f,1.5f
        );
        scaleXAnim.setDuration(500);

        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(
                answerImage,
                "ScaleY",
                1f,1.5f
        );
        scaleYAnim.setDuration(500);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(
                answerImage,
                "alpha",
                1f,0.6f
        );
        alphaAnim.setDuration(500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnim,scaleYAnim,alphaAnim);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                answerImage.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                answerImage.setVisibility(View.VISIBLE);
                if(answer)
                    answerImage.setImageDrawable(getResources().getDrawable(R.drawable.right));
                else
                    answerImage.setImageDrawable(getResources().getDrawable(R.drawable.wrong));
            }
        });
        animatorSet.start();

    }
    private void shapeExitAnimation(){

        ObjectAnimator translateXAnim = ObjectAnimator.ofFloat(
                shape,
                "translationX",
                0f,-1000f
        );
        translateXAnim.setDuration(500);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(
                shape,
                "alpha",
                1f,0.6f
        );
        alphaAnim.setDuration(500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateXAnim,alphaAnim);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                    shapeEnterAnimation();

            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        animatorSet.start();

    }
    private void shapeEnterAnimation(){

        ObjectAnimator translateXAnim = ObjectAnimator.ofFloat(
                shape,
                "translationX",
                1000f,0f
        );
        translateXAnim.setDuration(500);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(
                shape,
                "alpha",
                0.6f,1f
        );
        alphaAnim.setDuration(500);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateXAnim,alphaAnim);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);


            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                startGame();
            }
        });
        animatorSet.start();
    }

}
