package com.example.dino.Hilos;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameLoopThread extends Thread{

    static final long FPS =10;
    private boolean runnig;
    private SurfaceView view;
    private SurfaceHolder sh;

    public GameLoopThread(SurfaceView view) {
        this.runnig = false;
        this.view=view;
        this.sh=view.getHolder();
    }
    public void setRunning(boolean runnig){
        this.runnig=runnig;
    }

    @Override
    public void run() {
        long ticksPS=1000 / FPS;
        long startTime;
        long sleepTime;

        while(runnig){
            Canvas canvas=null;
            startTime = System.currentTimeMillis();
            try {
                canvas=sh.lockCanvas();
                //Internamente llama al metodo onDraw de la vista
                if(canvas!=null) {
                    synchronized (sh) {
                        view.postInvalidate();
                    }
                }
            }finally {
                if(canvas!=null)
                    sh.unlockCanvasAndPost(canvas);
            }
            sleepTime=ticksPS-(System.currentTimeMillis()-startTime);
            try{
                if(sleepTime>0)
                    sleep(sleepTime);
                else
                    sleep(10);
            }
            catch (Exception e){

            }
        }
    }
}
