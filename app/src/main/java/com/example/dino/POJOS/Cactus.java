package com.example.dino.POJOS;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.dino.GameView;

import java.util.LinkedList;
import java.util.Random;

public class Cactus {

    private static GameView gameview;
    private Bitmap bmp;
    private int PosX=0;
    private int PosY;
    private int xSpeed=30;
    private static Random rnd;
    private static LinkedList<Cactus> cactuses=new LinkedList<Cactus>();




    public Cactus(GameView gameView, Bitmap bmp) {
        this.gameview=gameView;
        this.bmp=bmp;
        rnd=new Random();
        PosX = gameView.getWidth();
        PosY = (int) (gameview.getHeight()*0.72);
    }

    public static Cactus getCactus(GameView gameView, Bitmap bmp){
        if(cactuses.isEmpty()){
            Log.i("Cactus","Cactus Nuevo Creado");
            return new Cactus(gameView,bmp);
        }else{
            Cactus cactus=cactuses.removeFirst();
            cactus.PosX=gameView.getWidth();
            cactus.PosY=(int) (gameview.getHeight()*0.72);
            Log.i("Cactus","Cactus Reciclado Creado");
            return cactus;
        }
    }

    private void update(){
        PosX = PosX -xSpeed;
    }

    public void onDraw(Canvas canvas){
        update();
        canvas.drawBitmap(bmp,PosX, PosY,null);
    }

    public void recycle(){
        cactuses.add(this);
        Log.i("Cactus","Cactus Reciclado");
    }

    public int getPosX() {
        return PosX;
    }

    public int getAncho() {
        return bmp.getWidth();
    }

    public Rect getBounds(){
        return new Rect(PosX, PosY, PosX + bmp.getWidth(), PosY + bmp.getHeight());
    }
}
