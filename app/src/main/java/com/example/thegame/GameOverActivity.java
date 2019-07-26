package com.example.thegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button StartGameAgain;
    private TextView  DisplayScore;
    private int score;
    private int lastScore, best1, best2, best3;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = preferences.edit();

        score = getIntent().getExtras().getInt("score");
//        editor.putInt("score", lastScore);
//        editor.apply();
        lastScore= score;
        best1 = preferences.getInt("best1", 0);
        best2 = preferences.getInt("best2", 0);
        best3 = preferences.getInt("best3", 0);

        StartGameAgain = (Button) findViewById(R.id.play_again_btn);
        DisplayScore = (TextView) findViewById(R.id.displayScore);

        if (lastScore > best3){
            editor.putInt("best3", best3);
            editor.apply();
        }
        if (lastScore > best2){
            int temp = best2;
            best2 = lastScore;
            best3 = temp;
            editor.putInt("best3", best3);
            editor.putInt("best2", best2);

            editor.apply();
        }
        if (lastScore > best1){
            int temp = best1;
            best1 = lastScore;
            best2 = temp;
            editor.putInt("best2", best2);
            editor.putInt("best1", best1);

            editor.apply();
        }


        DisplayScore.setText("Score: " + score +"\n"
                +"BEST Score: " + best1 + "\n"
                +"2nd Best  : " + best2 + "\n"
                +"3rd Best  : " + best3 + "\n");





        StartGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainIntent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });


    }
}
