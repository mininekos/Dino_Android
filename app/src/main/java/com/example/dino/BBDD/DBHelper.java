package com.example.dino.BBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String NOMBREBBDD = "HighScore.db";

    public DBHelper(Context context) {
        super(context, NOMBREBBDD, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE \"HighScore\" (\n" +
                "\t\"MaxScore\"\tINTEGER NOT NULL UNIQUE,\n" +
                "\tPRIMARY KEY(\"MaxScore\")\n" +
                ")");

        ContentValues values = new ContentValues();
        values.put("MaxScore", 0);
        sqLiteDatabase.insert("HighScore", null, values);


        System.out.println("Se creó la BBDD");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void updateHighScore(int maxScore){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c= db.rawQuery("Update HighScore SET MaxScore="+maxScore+"", null);
        c.moveToFirst();
        c.close();
    }

    public int getHighScore(){
        int maxScore=0;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c= db.rawQuery("SELECT MaxScore FROM HighScore",null);
        if(c.moveToFirst()){
            maxScore=c.getInt(0);
        }
        c.close();

        return maxScore;
    }


}
