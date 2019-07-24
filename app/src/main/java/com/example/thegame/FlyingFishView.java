package com.example.thegame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View {

    private Bitmap fish[] = new Bitmap[2];
    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    private int fishX = 10;
    private int fishY;
    private int fishSpeed;
    private int canvasWidth, canvasHeight;

    private int score, lifeCounterOfFish;

    private int yellowX, yellowY, yellowSpeed =16;
    private Paint yellowPaint = new Paint();

    private int greenX, greeeY, greenSpeed = 20;
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 25;
    private Paint redPaint = new Paint();

    private int alienX, alienY, alienSpeed =25;
    private Bitmap alien[] = new Bitmap[3];
    private Paint clapPaint = new Paint();

    private boolean touch = false;

    public FlyingFishView(Context context) {



        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.man1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.man3);
        alien[0] = BitmapFactory.decodeResource(getResources(),R.drawable.alien2_s);
        alien[1] = BitmapFactory.decodeResource(getResources(),R.drawable.ufo_s);
        alien[2] = BitmapFactory.decodeResource(getResources(),R.drawable.cop1_s);







        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.a51_90);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);


        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        clapPaint.setColor(Color.WHITE);
        clapPaint.setTextSize(70);
        clapPaint.setTypeface(Typeface.DEFAULT_BOLD);
        clapPaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY = 550;
        score = 0;
        lifeCounterOfFish = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(backgroundImage, 0, 0, null);

        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() *3;
        fishY = fishY + fishSpeed;
        if (fishY < minFishY)
        {
            fishY = minFishY;
        }
        if (fishY > maxFishY){

            fishY = maxFishY;
        }
        fishSpeed = fishSpeed + 2;

        if (touch){
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        }
        else
        {
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }

        yellowX = yellowX - yellowSpeed;

        if (hitBallChecker(yellowX, yellowY))
        {
            score = score +10;
            yellowX = -100;
        }

        if (yellowX < 0)
        {
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random()*(maxFishY-minFishY)) + minFishY;

        }
        canvas.drawBitmap(alien[1], yellowX, yellowY, null);

        //canvas.drawText("Clap dem cheeks", yellowX,yellowY,clapPaint);

        //canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);

        //************************************************************************
        alienY = alienX - alienSpeed;

        if (hitBallChecker(alienX,alienY))
        {
            score  = score +100;
            alienX = -100;
        }
        if (alienX < 0)
        {
            alienX = canvasWidth +21;
            alienY = (int) (Math.floor(Math.random()*(maxFishY-minFishY)) + minFishY);
        }
        //canvas.drawBitmap(alien[0], alienX, alienY, null);
        //canvas.drawText("Clap", alienX,alienY,clapPaint);


        greenX  = greenX - greenSpeed;

        if (hitBallChecker(greenX, greeeY))
        {
            score = score +20;
            greenX = -100;
        }

        if (greenX < 0)
        {
            greenX = canvasWidth + 21;
            greeeY = (int) Math.floor(Math.random()*(maxFishY-minFishY)) + minFishY;
        }
        canvas.drawBitmap(alien[0], greenX, greeeY, null);

        //canvas.drawCircle(greenX, greeeY, 25, greenPaint);

        redX  = redX - redSpeed;

        if (hitBallChecker(redX, redY))
        {
            redX = -100;
            lifeCounterOfFish--;

            if (lifeCounterOfFish == 0) {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score", score);
                getContext().startActivity(gameOverIntent);
            }
        }

        if (redX < 0)
        {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random()*(maxFishY-minFishY)) + minFishY;
        }
        canvas.drawBitmap(alien[2], redX, redY, null);
        //canvas.drawCircle(redX, redY, 30, redPaint);

        canvas.drawText("Score: " + score, 20, 60, scorePaint);

        for (int i = 0; i<3; i++)
        {
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < lifeCounterOfFish)
            {
                canvas.drawBitmap(life[0], x, y, null);
            }
            else
            {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }

//        canvas.drawBitmap(life[0], 580, 10, null);
//        canvas.drawBitmap(life[0], 680, 10, null);
//        canvas.drawBitmap(life[0], 780, 10, null);
    }

    public boolean hitBallChecker(int x, int y){
        if (fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight()) ){

            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touch = true;

            fishSpeed = -22;
        }
        return  true;
    }
}
