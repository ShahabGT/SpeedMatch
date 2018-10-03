package ir.shahabazimi.speedmatch.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ir.shahabazimi.speedmatch.R;
import ir.shahabazimi.speedmatch.database.Highscore;
import ir.shahabazimi.speedmatch.database.MyDatabase;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class ScoreDialog extends Dialog {

    public static String score;
    public static String game;
    private Context context;
    private TextView time,scoreText,highscore;
    private Button button;
    private String name;
    private int h,w;
    private MyDatabase myDatabase;

    public ScoreDialog(@NonNull Context context,String username) {
        super(context);
        this.context=context;
        this.name=username;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoredialog);

        init();
        setTexts();

    }

    private void init(){
        myDatabase = Room.databaseBuilder(context, MyDatabase.class, "userDB").allowMainThreadQueries().build();
        time = findViewById(R.id.score_text);
        scoreText = findViewById(R.id.score_text2);
        highscore = findViewById(R.id.score_newhighscore);

        button = findViewById(R.id.score_ok);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                ((Activity)context).finish();
            }
        });

    }

    private void setTexts(){
        time.setText(context.getString(R.string.scoredialog_text,name));
        scoreText.setText(context.getString(R.string.scoredialog_text2,score));
        if(game.equals("numbers")){
            if(Integer.parseInt(score)> getNumbersHighScore()){
                createConfetti();
                highscore.setVisibility(View.VISIBLE);

            }

        }else {
            if(Integer.parseInt(score)> getShapesHighScore()){
                createConfetti();
                highscore.setVisibility(View.VISIBLE);

            }

        }


        insertIntoDatabase();

    }


    private int getNumbersHighScore() {
        return myDatabase.myDao().numbersHighestscore();
    }
    private int getShapesHighScore() {
        return myDatabase.myDao().shapesHighestscore();
    }

    private void insertIntoDatabase(){

        Highscore user =  new Highscore();
        user.setName(name);
        user.setScore(Integer.parseInt(score));
        if(game.equals("numbers"))
        user.setGame("numbers");
        else
            user.setGame("shapes");

        myDatabase.myDao().addUser(user);



    }

    private void createConfetti(){
        KonfettiView konfettiView = findViewById(R.id.viewKonfetti);
        konfettiView.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f,5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12,5))
                .setPosition(w+0f, w+700f, w+0f, h+700f)
                .stream(300, 3000L);


    }


    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        h = getWindow().getDecorView().getHeight();
        w = getWindow().getDecorView().getWidth();
    }

    @Override
    public void onBackPressed() {
    }
}
