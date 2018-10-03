package ir.shahabazimi.speedmatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import ir.shahabazimi.speedmatch.Fragments.NumbersFragment;
import ir.shahabazimi.speedmatch.Fragments.ShapesFragment;

public class GameActivity extends AppCompatActivity {

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        init();

    }

    private void init(){
        bundle=new Bundle();
        bundle.clear();
        bundle.putString("username", getExtras("username"));

        switch (getExtras("game")){


            case "numbers":
                NumbersFragment numbersFragment = new NumbersFragment();
                numbersFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.game_frame,numbersFragment).commit();
                break;


            case "shapes":

                ShapesFragment shapesFragment = new ShapesFragment();
                shapesFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.game_frame,shapesFragment).commit();
                break;


        }


    }

    private String getExtras(String tag){
        Bundle bundle = getIntent().getExtras();
        return bundle.getString(tag);

    }
}
