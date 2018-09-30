package com.example.auzan.kamusku;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by auzan on 9/27/2018.
 * Github: @auzanassdq
 */
public class Kamus implements Parcelable {

    private int id;
    private String words;
    private String means;

    public Kamus(){

    }

    public Kamus(String words, String means){
        this.words = words;
        this.means = means;
    }

    public Kamus(int id, String words, String means){
        this.id = id;
        this.words = words;
        this.means = means;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getMeans() {
        return means;
    }

    public void setMeans(String means) {
        this.means = means;
    }

    protected Kamus(Parcel in) {
        id = in.readInt();
        words = in.readString();
        means = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(words);
        dest.writeString(means);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Kamus> CREATOR = new Creator<Kamus>() {
        @Override
        public Kamus createFromParcel(Parcel in) {
            return new Kamus(in);
        }

        @Override
        public Kamus[] newArray(int size) {
            return new Kamus[size];
        }
    };
}
