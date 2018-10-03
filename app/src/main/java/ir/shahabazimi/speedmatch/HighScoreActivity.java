package ir.shahabazimi.speedmatch;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import ir.shahabazimi.speedmatch.Adapters.highscoreAdapter;
import ir.shahabazimi.speedmatch.database.MyDatabase;

public class HighScoreActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private TextView title;
    private highscoreAdapter adapter;
    private RecyclerView recyclerView;
    private MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_high_score);

        init();
    }


    private void init(){
        myDatabase = Room.databaseBuilder(HighScoreActivity.this, MyDatabase.class, "userDB").allowMainThreadQueries().build();
        title = findViewById(R.id.highscore_title);
        recyclerView = findViewById(R.id.highscore_recycler);

        navigation =  findViewById(R.id.highscore_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.menu_numbers);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_numbers:
                    title.setText(getString(R.string.numbers_highscore));
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HighScoreActivity.this);
                    adapter = new highscoreAdapter(HighScoreActivity.this,myDatabase.myDao().getNumbersHighscores());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    break;

                case R.id.menu_shapes:
                    title.setText(getString(R.string.shapes_highscore));
                    RecyclerView.LayoutManager layoutManagerx = new LinearLayoutManager(HighScoreActivity.this);
                    adapter = new highscoreAdapter(HighScoreActivity.this,myDatabase.myDao().getShapesHighscores());
                    recyclerView.setLayoutManager(layoutManagerx);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    break;

            }
            return false;
        }
    };

}
