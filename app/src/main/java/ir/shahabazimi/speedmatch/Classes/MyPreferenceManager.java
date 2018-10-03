package ir.shahabazimi.speedmatch.Classes;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferenceManager {

    private static MyPreferenceManager instanse=null;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;


    public static MyPreferenceManager getInstance(Context context){
            if(instanse==null){
                instanse =  new MyPreferenceManager(context);
            }

            return instanse;

    }


    private MyPreferenceManager(Context context){

        sp = context.getSharedPreferences("SharedPreference",Context.MODE_PRIVATE);
        editor = sp.edit();

    }

}
