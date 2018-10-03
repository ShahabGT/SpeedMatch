package ir.shahabazimi.speedmatch.Fragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import ir.shahabazimi.speedmatch.Dialogs.ScoreDialog;
import ir.shahabazimi.speedmatch.R;

public class NumbersFragment extends Fragment {


    private TextView animText,score,timer;
    private Button left,right,equal;
    private ImageView image;
    private RelativeLayout gamelayout;
    private CountDownTimer countDownTimer;

    private int leftNumber,rightNumber;
    private int count=3;
    private boolean answer=false;
    private long gameTime = 60000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_numbers, container, false);

        init(v);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startGameAnimation();


    }

    private void init(View v){
        animText = v.findViewById(R.id.numbers_startanim);
        score = v.findViewById(R.id.numbers_score);
        timer = v.findViewById(R.id.numbers_timer);

        left=v.findViewById(R.id.numbers_leftnum);
        right=v.findViewById(R.id.numbers_rightnum);
        equal=v.findViewById(R.id.numbers_equalnum);

        image= v.findViewById(R.id.numbers_image);

        gamelayout= v.findViewById(R.id.numbers_game_layout);

    }

    private void answerAnimation(final boolean answer){
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(
                image,
                "ScaleX",
                1f,1.5f
        );
        scaleXAnim.setDuration(500);

        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(
                image,
                "ScaleY",
                1f,1.5f
        );
        scaleYAnim.setDuration(500);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(
                image,
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
                image.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                image.setVisibility(View.VISIBLE);
                if(answer)
                    image.setImageDrawable(getResources().getDrawable(R.drawable.right));
                else
                    image.setImageDrawable(getResources().getDrawable(R.drawable.wrong));
            }
        });
        animatorSet.start();

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
                else {
                    gamelayout.setVisibility(View.VISIBLE);
                    animText.setVisibility(View.GONE);
                    startGame();
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

    private void startGame(){
        leftNumber = generateNumbers();
        rightNumber = generateNumbers();
        left.setText(leftNumber+"");
        right.setText(rightNumber+"");
        equal.setText("=");

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leftNumber>rightNumber){
                    int res = Integer.parseInt(score.getText().toString())+1;
                    score.setText(res+"");
                    answer=true;
                }
                answerAnimation(answer);
                startGame();
                answer=false;
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leftNumber<rightNumber){
                    int res = Integer.parseInt(score.getText().toString())+1;
                    score.setText(res+"");
                    answer=true;
                }
                answerAnimation(answer);
                startGame();
                answer=false;
            }
        });
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leftNumber==rightNumber){
                    int res = Integer.parseInt(score.getText().toString())+1;
                    score.setText(res+"");
                    answer=true;
                }
                answerAnimation(answer);
                startGame();
                answer=false;
            }
        });


    }

    private int generateNumbers(){
        Random random =  new Random();
        return random.nextInt(50);
    }

    private String convertToTimeFormat(long millisecond){
        Date date = new Date(millisecond);
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
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
                scoreDialog.game="numbers";
                scoreDialog.show();

            }
        };
        countDownTimer.start();
    }
}
