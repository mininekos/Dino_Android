package com.example.dino.Hilos;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.dino.GameView;
import com.example.dino.POJOS.TRex;
import com.example.dino.R;

public class HiloDino extends Thread {


    private boolean runnig;
    private boolean pause;
    private Bitmap bmpTRex;
    private GameView gameView;
    private TRex tRex;
    private boolean isJumping;

    public HiloDino(GameView gameView) {
        runnig = false;
        pause = false;
        this.gameView = gameView;
        tRex = new TRex(gameView, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dino_anda_1));
        isJumping = false;

    }


    public void setRunning(boolean runnig) {
        this.runnig = runnig;
        if(!runnig){
            bmpTRex = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dino_muerto);
            tRex.setBmp(bmpTRex);
        }

    }

    public void run() {
        while (runnig) {
            if (!pause) {
                try {
                    if (!isJumping && tRex.getSalto() == 0) {
                        sleep(100);
                        bmpTRex = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dino_anda_1);
                        tRex.setBmp(bmpTRex);
                    }
                    if (!isJumping && tRex.getSalto() == 0) {
                        sleep(100);
                        bmpTRex = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dino_anda_2);
                        tRex.setBmp(bmpTRex);
                    }
                    if (isJumping) {
                        bmpTRex = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dino_salto);
                        tRex.setBmp(bmpTRex);
                        tRex.setJumping(true);
                    }

                    if (tRex.getSalto() >= 200) {
                        isJumping = false;
                        tRex.setJumping(false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public TRex gettRex() {
        return tRex;
    }

}



