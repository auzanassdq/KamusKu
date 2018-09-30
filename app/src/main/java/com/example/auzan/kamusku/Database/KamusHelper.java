package com.example.auzan.kamusku.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.auzan.kamusku.Kamus;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.auzan.kamusku.Database.DatabaseContract.TABLE_KAMUS;
import static com.example.auzan.kamusku.Database.DatabaseContract.WordColumns.MEAN;
import static com.example.auzan.kamusku.Database.DatabaseContract.WordColumns.WORD;

/**
 * Created by auzan on 9/29/2018.
 * Github: @auzanassdq
 */
public class KamusHelper {

    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public KamusHelper(Context context){
        this.context = context;
    }

    public KamusHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<Kamus> getDataByWord (String word) {
        Cursor cursor = database.query(TABLE_KAMUS, null, WORD +
                " LIKE ?", new String[]{word}, null, null, _ID +
                " ASC", null);
        cursor.moveToFirst();
        ArrayList<Kamus> arrayList = new ArrayList<>();
        Kamus kamus;
        if (cursor.getCount() > 0){
            do {
                kamus = new Kamus();
                kamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamus.setWords(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                kamus.setMeans(cursor.getString(cursor.getColumnIndexOrThrow(MEAN)));

                arrayList.add(kamus);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Kamus> getAllData(){
        Cursor cursor = database.query(TABLE_KAMUS, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<Kamus> arrayList = new ArrayList<>();
        Kamus kamus;
        if (cursor.getCount() > 0) {
            do {
                kamus = new Kamus();
                kamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamus.setWords(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                kamus.setMeans(cursor.getString(cursor.getColumnIndexOrThrow(MEAN)));

                arrayList.add(kamus);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Kamus kamus){
        ContentValues initialValues = new ContentValues();
        initialValues.put(WORD, kamus.getWords());
        initialValues.put(MEAN, kamus.getMeans());
        return database.insert(TABLE_KAMUS, null, initialValues);
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(Kamus kamus){
        String sql = "INSERT INTO " + TABLE_KAMUS + " (" + WORD + ", " + MEAN + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamus.getWords());
        stmt.bindString(2, kamus.getMeans());
        stmt.execute();
        stmt.clearBindings();

    }

    public int update(Kamus kamus){
        ContentValues args = new ContentValues();
        args.put(WORD, kamus.getWords());
        args.put(MEAN, kamus.getMeans());
        return database.update(TABLE_KAMUS, args, _ID + "= '" + kamus.getId() + "'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_KAMUS, _ID + " = '" + id + "'", null);
    }


}
