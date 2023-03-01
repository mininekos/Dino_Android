package com.example.dino.POJOS;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.dino.GameView;

public class Score {

    private int score;
//    private int highScore;
    private GameView gameview;
    private int posX;
    private int posY;
    private String texto;

    public Score(GameView gameview, int posX, int posY) {
        this.gameview=gameview;
        score=0;
        this.posX=posX;
        this.posY=posY;
        texto="";
    }
    public Score(GameView gameview, int score,int posX, int posY) {
        this.gameview=gameview;
        this.score=score;
        this.posX=posX;
        this.posY=posY;
        texto="HI: ";
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void resetScore(){
        score=0;
    }

    public void onDraw(Canvas canvas, Paint paint){
        canvas.drawText(texto+""+score,posX,posY,paint);
    }

}
