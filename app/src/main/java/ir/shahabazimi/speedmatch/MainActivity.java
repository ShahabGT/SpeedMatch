package ir.shahabazimi.speedmatch;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import ir.shahabazimi.speedmatch.Dialogs.StartDialog;
import ir.shahabazimi.speedmatch.Classes.UsernameListener;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;


public class MainActivity extends AppCompatActivity {

    private LinearLayout highscore;
    private LinearLayout numbers;
    private LinearLayout shapes;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initViews();


    }


    private void initViews(){

        highscore = findViewById(R.id.main_highscore);
        numbers = findViewById(R.id.main_numbers);
        shapes = findViewById(R.id.main_shapes);

        clickListeners();

    }

    private  void clickListeners(){
        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,HighScoreActivity.class));
            }
        });

        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StartDialog startDialog = new StartDialog(MainActivity.this, new UsernameListener() {
                    @Override
                    public void setUserName(String name) {
                        Intent intent = new Intent(MainActivity.this,GameActivity.class);
                        intent.putExtra("game","numbers");
                        intent.putExtra("username",name);
                        startActivity(intent);
                    }
                });
                startDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                startDialog.show();
            }
        });

        shapes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartDialog startDialog = new StartDialog(MainActivity.this, new UsernameListener() {
                    @Override
                    public void setUserName(String name) {
                        Intent intent = new Intent(MainActivity.this,GameActivity.class);
                        intent.putExtra("game","shapes");
                        intent.putExtra("username",name);
                        startActivity(intent);
                    }
                });
                startDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                startDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        MainActivity.this.finish();
    }
}
