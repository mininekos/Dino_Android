package com.example.dino.POJOS;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;

import com.example.dino.GameView;

public class TRex {

    private GameView gameview;
    private Bitmap bmp;
    private int PosX;
    private int PosY;
    private int salto;
    private boolean isJumping;

    public TRex(GameView gameView, Bitmap bmp) {
        this.gameview=gameView;
        this.bmp=bmp;
        this.isJumping=false;
        this.salto=0;
        PosX = (int) (gameview.getWidth()*0.05);
        PosY = (int) (gameview.getHeight()*0.7);
    }

    public void onDraw(Canvas canvas){
        update();
        canvas.drawBitmap(bmp,PosX, PosY,null);
    }

    private void update(){
        if (isJumping){
            PosY-=20;
            salto+=20;
        }else if (salto>=20 && !isJumping){
            PosY+=20;
            salto-=20;
            isJumping=false;
        }

    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public Rect getBounds(){
        return new Rect(PosX, PosY, PosX + bmp.getWidth(), PosY + bmp.getHeight());
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    public int getSalto() {
        return salto;
    }

    public boolean isJumping() {
        return isJumping;
    }
}
