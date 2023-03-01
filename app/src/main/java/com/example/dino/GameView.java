package com.example.dino;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.dino.BBDD.DBHelper;
import com.example.dino.Hilos.GameLoopThread;
import com.example.dino.Hilos.HiloColision;
import com.example.dino.Hilos.HiloDino;
import com.example.dino.Hilos.HiloSuelo;
import com.example.dino.Hilos.TemporizadorScore;
import com.example.dino.Hilos.GenerazionCactus;
import com.example.dino.Hilos.GeneracionNubes;
import com.example.dino.POJOS.Cactus;
import com.example.dino.POJOS.Nube;
import com.example.dino.POJOS.Score;
import com.example.dino.POJOS.Suelo;
import com.example.dino.POJOS.TRex;

import java.util.ArrayList;
import java.util.Iterator;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    private Context context;
    private GameLoopThread gameThread;
    private ArrayList<Nube> nubes;
    private ArrayList<Cactus> cactuses;
    private ArrayList<Suelo> suelos;
    private Score score;
    private Score highScore;
    private TemporizadorScore temporizadorScore;
    private HiloDino hiloDino;
    private GenerazionCactus hiloCactus;
    private GeneracionNubes hiloNube;
    private HiloColision hiloColision;
    private HiloSuelo hiloSuelo;
    private Bitmap bmpGameover;
    private Bitmap bmpRestart;
    private boolean salto=false;
    private final Object lock = new Object();
    private boolean gameOver=false;
    private DBHelper dbHelper;
    private MediaPlayer media;
    private SoundPool soundPool;
    private int musicaDead;
    private int musicaJump;
    private int musicaScore;


    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setBackgroundColor(Color.WHITE);
        this.context=context;
        dbHelper=new DBHelper(context);
        media=MediaPlayer.create(context,R.raw.musica_fondo_reducida);
        media.setLooping(true);
        media.start();

        soundPool=new SoundPool.Builder().setMaxStreams(1).build();
        musicaDead=soundPool.load(context,R.raw.dead,1);
        musicaJump=soundPool.load(context,R.raw.jump,1);
        musicaScore=soundPool.load(context,R.raw.scoreup,1);
    }

    @Override
    protected void onDraw(Canvas canvas){
        Paint p = new Paint();
        p.setAntiAlias(true);

        Paint pText = new Paint();
        pText.setAntiAlias(true);
        pText.setColor(Color.BLACK);
        pText.setTextSize(50);

        canvas.drawColor(Color.rgb(247, 247, 247));

        //Nubes
        Iterator<Nube> iter = nubes.iterator();
        while (iter.hasNext()) {
            Nube nube = iter.next();
            nube.onDraw(canvas);
            synchronized (lock) {
                if (nube.getPosX() < 0 - nube.getAncho()) {
                    nube.recycle();
                    iter.remove();
                }
            }
        }

        //Suelo
        Iterator<Suelo> iterSuelo = suelos.iterator();
        while (iterSuelo.hasNext()) {
            Suelo suelo = iterSuelo.next();
            suelo.onDraw(canvas);
            synchronized (lock) {
                if (suelo.getPosX() < 0 - suelo.getAncho()) {
                    suelo.recycle();
                    iterSuelo.remove();
                }
            }
        }

        //Cactus
        Iterator<Cactus> iterCactus = cactuses.iterator();
        while (iterCactus.hasNext()) {
            Cactus cactus = iterCactus.next();
            cactus.onDraw(canvas);
            synchronized (lock) {
                if (cactus.getPosX() < 0 - cactus.getAncho()) {
                    cactus.recycle();
                    iterCactus.remove();
                }
            }
        }


        //Colision
        if(hiloColision.isColision()){
            soundPool.play(musicaDead,1,1,1,0,1);
            destroy();
            setHighScoreDB();
            gameOver=true;
            canvas.drawBitmap(bmpGameover,(int) (getWidth()/2-bmpGameover.getWidth()/2),(int) (getHeight()/2),p);
            canvas.drawBitmap(bmpRestart,(int) (getWidth()/2-bmpRestart.getWidth()/2),(int) (getHeight()/2+bmpGameover.getHeight()+20),p);
        }

        //T-Rex
        hiloDino.gettRex().onDraw(canvas);
        if(salto) {
            if (hiloDino.gettRex().getSalto() >= 200) {
                hiloDino.setJumping(false);
            }
            if (hiloDino.gettRex().getSalto() == 0 && !hiloDino.isJumping() ) {
                salto = false;
            }
        }

        score.setScore(temporizadorScore.getTiempo());
        if(score.getScore()%100==0 && score.getScore()!=0){
            soundPool.play(musicaScore,1,1,1,0,1);
        }
        score.onDraw(canvas,pText);
        highScore.onDraw(canvas,pText);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!salto){
                    salto=true;
                    hiloDino.setJumping(true);
                    soundPool.play(musicaJump,1,1,1,0,1);
                }
                if(gameOver && event.getX()>getWidth()/2-bmpRestart.getWidth()/2
                        && event.getX()<getWidth()/2+bmpRestart.getWidth()/2
                        && event.getY()>getHeight()/2+bmpGameover.getHeight()+20
                        && event.getY()<getHeight()/2+bmpGameover.getHeight()+20+bmpRestart.getHeight()){
                    ((Activity)context).recreate();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        nubes=new ArrayList<>();
        cactuses=new ArrayList<>();
        suelos=new ArrayList<>();

        createThreads();

        bmpGameover= BitmapFactory.decodeResource(getResources(),R.drawable.gameover_text);
        bmpRestart= BitmapFactory.decodeResource(getResources(),R.drawable.replay_button);




    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        destroy();
        media.stop();
    }

    public ArrayList<Nube> getNubes() {
        return nubes;
    }

    public ArrayList<Cactus> getCactuses() {
        return cactuses;
    }

    public ArrayList<Suelo> getSuelos() {
        return suelos;
    }

    public boolean colision(){
        for (Cactus cactus:cactuses) {
            Rect tRexRect = hiloDino.gettRex().getBounds();
            Rect cactusRect = cactus.getBounds();
            if(tRexRect.intersect(cactusRect)){
                return true;
            }
        }
        return false;
    }

    public void destroy(){
        gameThread.setRunning(false);
        temporizadorScore.setRunning(false);
        hiloCactus.setRunning(false);
        hiloNube.setRunning(false);
        hiloDino.setRunning(false);
        hiloColision.setRunning(false);
        hiloSuelo.setRunning(false);

    }

    public void createThreads(){
        hiloColision=new HiloColision(this);
        hiloColision.setRunning(true);
        hiloColision.start();

        gameThread=new GameLoopThread(this);
        gameThread.setRunning(true);
        gameThread.start();

        temporizadorScore =new TemporizadorScore();
        temporizadorScore.setRunning(true);
        temporizadorScore.start();

        hiloDino =new HiloDino(this);
        hiloDino.setRunning(true);
        hiloDino.start();

        hiloCactus =new GenerazionCactus(this);
        hiloCactus.setRunning(true);
        hiloCactus.start();

        hiloNube =new GeneracionNubes(this);
        hiloNube.setRunning(true);
        hiloNube.start();

        hiloSuelo =new HiloSuelo(this);
        hiloSuelo.setRunning(true);
        hiloSuelo.start();

        score=new Score(this,(int) (getWidth()*0.9),(int) (getHeight()*0.1));
        highScore=new Score(this,dbHelper.getHighScore(),(int) (getWidth()*0.8),(int) (getHeight()*0.1));
    }

    public void setHighScoreDB(){
        if(score.getScore()>highScore.getScore()){
            dbHelper.updateHighScore(score.getScore());
            highScore.setScore(score.getScore());
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
