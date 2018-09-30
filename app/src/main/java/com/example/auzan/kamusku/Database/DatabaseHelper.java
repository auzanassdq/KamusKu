package com.example.auzan.kamusku.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
//import static com.example.auzan.kamusku.Database.DatabaseContract.TABLE_KAMUS_EN;
import static com.example.auzan.kamusku.Database.DatabaseContract.TABLE_KAMUS;
import static com.example.auzan.kamusku.Database.DatabaseContract.WordColumns.MEAN;
import static com.example.auzan.kamusku.Database.DatabaseContract.WordColumns.WORD;

/**
 * Created by auzan on 9/29/2018.
 * Github: @auzanassdq
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbkamus";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE = "create table "+ TABLE_KAMUS +
            " ("+_ID+" integer primary key autoincrement, " +
            WORD+" text not null, " +
            MEAN+" text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
//        db.execSQL(CREATE_TABLE_EN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KAMUS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KAMUS_EN);
        onCreate(db);
    }
}
