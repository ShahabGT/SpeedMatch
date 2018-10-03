package ir.shahabazimi.speedmatch.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ir.shahabazimi.speedmatch.R;
import ir.shahabazimi.speedmatch.database.Highscore;

public class highscoreAdapter extends RecyclerView.Adapter<highscoreAdapter.ViewHolder> {

    private Context context;
    private List<Highscore> highscoreList;

    public highscoreAdapter(Context context, List<Highscore> highscoreList){

        this.context=context;
        this.highscoreList = highscoreList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.highscore_row,parent,false);

      return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Highscore highscore =  highscoreList.get(position);

        holder.name.setText(highscore.getName());
        holder.score.setText(highscore.getScore()+"");
        if(position<4) {
            switch (position) {
                case 0:
                    holder.image.setImageResource(R.mipmap.ic_first);
                    break;
                case 1:
                    holder.image.setImageResource(R.mipmap.ic_second);
                    break;
                case 2:
                    holder.image.setImageResource(R.mipmap.ic_third);
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return highscoreList.size();
    }

     public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name,score;
        private ImageView image;


         public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.row_name);
            score = v.findViewById(R.id.row_score);
             image = v.findViewById(R.id.row_image);

        }
    }
}
