package com.example.dino.POJOS;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.example.dino.GameView;

import java.util.LinkedList;
import java.util.Random;

public class Suelo {
    private static GameView gameview;
    private Bitmap bmp;
    private int PosX=0;
    private int PosY;
    private int xSpeed=30;
    private static Random rnd;
    private static LinkedList<Suelo> suelos =new LinkedList<Suelo>();



    public Suelo(GameView gameView, Bitmap bmp) {
        this.gameview=gameView;
        this.bmp=bmp;
        rnd=new Random();
        PosY = (int) (gameview.getWidth()*0.32);
        PosX =gameview.getWidth();
    }

    public static Suelo getSuelo(GameView gameView, Bitmap bmp){
        if(suelos.isEmpty()){
            Log.i("Suelo","Suelo Nuevo Creado");
            return new Suelo(gameView,bmp);

        }else{
            Log.i("Suelo","Suelo Reciclado Creado");
            Suelo suelo= suelos.removeFirst();
            suelo.PosX=gameView.getWidth()-30;
            suelo.PosY=(int) (gameview.getWidth()*0.32);

            return suelo;
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
        suelos.add(this);
        Log.i("Suelo","Suelo Reciclado");
    }

    public static void clear(){
        suelos.clear();
    }

    public int getPosX() {
        return PosX;
    }

    public int getAncho() {
        return bmp.getWidth();
    }

    public void setPosX(int posX) {
        PosX = posX;
    }
}

