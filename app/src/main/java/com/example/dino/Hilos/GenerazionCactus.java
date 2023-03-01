package com.example.dino.Hilos;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.dino.GameView;
import com.example.dino.POJOS.Cactus;
import com.example.dino.R;

import java.util.ArrayList;
import java.util.Random;

public class GenerazionCactus extends Thread{


    private boolean runnig;
    private boolean pause;
    private ArrayList<Cactus> cactus;
    private GameView gameView;
    private Bitmap bmpCactus;
    private Random random;
    private Cactus cactus1;
    private Random rnd;



    public GenerazionCactus(GameView gameView) {
        runnig=false;
        pause=false;
        cactus=gameView.getCactuses();
        this.gameView=gameView;
        random=new Random();
        cactus1=new Cactus(gameView, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.cactus1));
        random=new Random();

    }


    public void setRunning(boolean runnig){
        this.runnig=runnig;
        if(!runnig)
            cactus.clear();
    }

    public void run(){
        while(runnig) {
            if (!pause) {
                try {
                    if((random.nextInt(2)+1)%2==0)
                        bmpCactus = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.cactus2);
                    else
                        bmpCactus = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.cactus1);
                    cactus.add(cactus1.getCactus(gameView, bmpCactus));
                    sleep(random.nextInt(2000)+3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
