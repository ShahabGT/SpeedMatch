package ir.shahabazimi.speedmatch.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MyDao {


    @Insert
    void addUser(Highscore user);



    @Query("SELECT * FROM Highscore WHERE game='numbers' ORDER BY score DESC")
    List<Highscore> getNumbersHighscores();

    @Query("SELECT * FROM Highscore WHERE game='shapes' ORDER BY score DESC")
    List<Highscore> getShapesHighscores();



    @Query("SELECT score FROM Highscore where game='numbers' ORDER BY score DESC LIMIT 1 ")
    int numbersHighestscore();

    @Query("SELECT score FROM Highscore where game='shapes' ORDER BY score DESC LIMIT 1 ")
    int shapesHighestscore();








}
