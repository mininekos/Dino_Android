package com.example.dino.Hilos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.dino.GameView;
import com.example.dino.POJOS.Suelo;
import com.example.dino.R;

import java.util.ArrayList;
import java.util.Random;

public class HiloSuelo extends Thread{

    private boolean runnig;
    private boolean pause;
    private ArrayList<Suelo> suelos;
    private GameView gameView;
    private Bitmap bmpSuelo;
    private Suelo suelo;
    private Random random;
    private int numRandom;

    public HiloSuelo(GameView gameView) {
        runnig = false;
        pause = false;
        this.suelos=gameView.getSuelos();
        this.bmpSuelo= BitmapFactory.decodeResource(gameView.getResources(), R.drawable.land1);
        this.gameView = gameView;
        random=new Random();
        suelo = new Suelo(gameView, bmpSuelo);

    }

    public void setRunning(boolean runnig) {
        this.runnig = runnig;
        if(!runnig)
            suelos.clear();
    }

    public void run() {
        while (runnig) {
            if (!pause) {
                try {
                    if(suelos.size()==0)
                        createSueloInicial();
                    else if(suelos.get(suelos.size()-1).getPosX()<=gameView.getWidth()-bmpSuelo.getWidth()){
                        createSuelo();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void createSuelo() {
        BmpRandom();
        suelos.add(suelo.getSuelo(gameView, bmpSuelo));
    }

    private void createSueloInicial() {

        for(int i=0;i<gameView.getWidth();i+=bmpSuelo.getWidth()) {
            BmpRandom();
            Suelo sueloInicio = suelo.getSuelo(gameView, bmpSuelo);
            sueloInicio.setPosX(i);
            suelos.add(sueloInicio);
        }
    }

    private void BmpRandom(){
        numRandom=random.nextInt(3)+1;
        if (numRandom % 3 == 0)
            bmpSuelo = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.land2);
        else if (numRandom % 3 == 1)
            bmpSuelo = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.land1);
        else
            bmpSuelo = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.land3);
    }
}
