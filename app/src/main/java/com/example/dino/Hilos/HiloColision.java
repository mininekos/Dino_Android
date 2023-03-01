package com.example.dino.Hilos;

import com.example.dino.GameView;

public class HiloColision extends Thread{

    private boolean runnig;
    private boolean pause;
    private GameView gameView;
    private boolean colision;

    public HiloColision(GameView gameView) {
        runnig = false;
        pause = false;
        this.gameView = gameView;
        colision = false;
    }

    public void setRunning(boolean runnig) {
        this.runnig = runnig;
    }

    public void run() {
        while (runnig) {
            if (!pause) {
                try {
                    if(gameView.colision()){
                        colision=true;
                    }
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isColision() {
        return colision;
    }
}
