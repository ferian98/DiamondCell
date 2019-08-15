package com.example.android.diamondcell;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Jabatan implements Parcelable {
    private String mKode;
    private String mNama;
    private int mHakAkses;
    private ArrayList<KeyValuePair> mPasanganKolomNilai;
    private void buatParameterTabelJabatan(){
        mPasanganKolomNilai= new ArrayList<>();
        mPasanganKolomNilai.add(new KeyValuePair("kode",mKode));
        mPasanganKolomNilai.add(new KeyValuePair("nama",mNama));
        mPasanganKolomNilai.add(new KeyValuePair("hak_akses",String.valueOf(mHakAkses)));
    }

    public Jabatan(String mKode) {
        this.mKode = mKode;
        //Todo: Dapatkan data jabatan berdasarkan kode dari database
    }

    public Jabatan(String mKode, String mNama, int mHakAkses) {
        this.mKode = mKode;
        this.mNama = mNama;
        this.mHakAkses = mHakAkses;
    }

    protected Jabatan(Parcel in) {
        mKode = in.readString();
        mNama = in.readString();
        mHakAkses = in.readInt();
        buatParameterTabelJabatan();
    }

    public static final Creator<Jabatan> CREATOR = new Creator<Jabatan>() {
        @Override
        public Jabatan createFromParcel(Parcel in) {
            return new Jabatan(in);
        }

        @Override
        public Jabatan[] newArray(int size) {
            return new Jabatan[size];
        }
    };

    public String getmKode() {
        return mKode;
    }

    public void setmKode(String mKode) {
        this.mKode = mKode;
    }

    public String getmNama() {
        return mNama;
    }

    public void setmNama(String mNama) {
        this.mNama = mNama;
    }

    public int getmHakAkses() {
        return mHakAkses;
    }

    public void setmHakAkses(int mHakAkses) {
        this.mHakAkses = mHakAkses;
    }

    public boolean isAuthorized(int NilaiPembanding){
       //Todo: Tentukan Hak Akses CRUD Tabel
        return true;
    }
    public void save(){
        //Todo: Definisikan Proses Save
    }

    public void update(){
        //Todo: Definisikan Proses Update
    }

    public void fetch(){

        //Todo: Definisikan Proses Load Data
    }

    public void delete(){
        //Todo: Definisikan Proses Delete
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mKode);
        parcel.writeString(mNama);
        parcel.writeInt(mHakAkses);
    }
}
