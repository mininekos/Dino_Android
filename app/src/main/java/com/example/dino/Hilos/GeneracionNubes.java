package com.example.dino.Hilos;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.dino.GameView;
import com.example.dino.POJOS.Nube;
import com.example.dino.R;

import java.util.ArrayList;
import java.util.Random;

public class GeneracionNubes extends Thread {
    private boolean runnig;
    private boolean pause;
    private ArrayList<Nube> nubes;
    private GameView gameView;
    private Bitmap bmpNube;
    private Nube nube;
    private Random rnd;

    public GeneracionNubes(GameView gameView ) {
        runnig = false;
        pause = false;
        this.nubes=gameView.getNubes();
        this.bmpNube= BitmapFactory.decodeResource(gameView.getResources(), R.drawable.cloud);
        this.gameView = gameView;
        nube = new Nube(gameView, bmpNube);
        rnd=new Random();

    }


    public void setRunning(boolean runnig) {

        this.runnig = runnig;
        if(!runnig)
            nubes.clear();
    }

    public void run() {
        while (runnig) {
            if (!pause) {
                try {
                    nubes.add(nube.getNube(gameView, bmpNube));
                    sleep(rnd.nextInt(1000)+1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
