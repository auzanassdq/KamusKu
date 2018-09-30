package com.example.auzan.kamusku.Database;

import android.provider.BaseColumns;

/**
 * Created by auzan on 9/29/2018.
 * Github: @auzanassdq
 */
public class DatabaseContract {

    public static String TABLE_KAMUS = "table_kamus";
//    public static String TABLE_KAMUS_EN = "table_english";


    public static final class WordColumns implements BaseColumns {
        static String WORD = "word";
        static String MEAN = "mean";
    }
}
