package com.example.dino.POJOS;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.example.dino.GameView;

import java.util.LinkedList;
import java.util.Random;

public class Nube {
    private GameView gameview;
    private Bitmap bmp;
    private int PosX=0;
    private int PosY;
    private int xSpeed=15;
    private static Random rnd;
    private static LinkedList<Nube> nubes=new LinkedList<Nube>();



    public Nube(GameView gameView, Bitmap bmp) {
        this.gameview=gameView;
        this.bmp=bmp;
        rnd=new Random();
        PosY = rnd.nextInt(gameview.getHeight()/2);
        PosX =gameview.getWidth();
    }

    public static Nube getNube(GameView gameView, Bitmap bmp){
        if(nubes.isEmpty()){
            Log.i("Nube","Nube Nueva Creada");
            return new Nube(gameView,bmp);

        }else{
            Log.i("Nube","Nube Reciclada Creada");
            Nube nube=nubes.removeFirst();
            nube.PosX=gameView.getWidth();
            nube.PosY=rnd.nextInt(gameView.getHeight()/2);

            return nube;
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
        nubes.add(this);
        Log.i("Nube","Nube Reciclada");
    }

    public static void clear(){
        nubes.clear();
    }

    public int getPosX() {
        return PosX;
    }

    public int getAncho() {
        return bmp.getWidth();
    }
}
