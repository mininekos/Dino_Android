package com.example.dino.Hilos;

public class TemporizadorScore extends Thread{

    private int tiempo;
    private boolean runnig;
    private boolean pause;

    public TemporizadorScore(){
        this.tiempo=0;
        runnig=false;
        pause=false;
    }


    public void setRunning(boolean runnig){
        this.runnig=runnig;
    }

    public void run(){
        while(runnig) {
            if (!pause) {
                try {
                    sleep(100);
                    tiempo++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public int getTiempo(){
        return tiempo;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }


}
