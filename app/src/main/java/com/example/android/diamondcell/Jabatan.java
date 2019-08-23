package com.example.android.diamondcell;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Jabatan implements Parcelable {
    private String mKode;
    private String mNama;
    private int mHakAkses;
    private ArrayList<KeyValuePair> mPasanganKolomNilai;
    public final static String NAMA_TABEL="tbjabatan";
    private DatabaseClass<Jabatan> mDatabase;
    private void buatParameterTabelJabatan(){
        mPasanganKolomNilai= new ArrayList<>();
        mPasanganKolomNilai.add(new KeyValuePair("kode",mKode));
        mPasanganKolomNilai.add(new KeyValuePair("nama",mNama));
        mPasanganKolomNilai.add(new KeyValuePair("hak_akses",String.valueOf(mHakAkses)));
        mDatabase=new DatabaseClass<>();
    }

    public Jabatan(String mKode) {
        this.mKode = mKode;
        //Todo: Dapatkan data jabatan berdasarkan kode dari database
        buatParameterTabelJabatan();
    }

    public Jabatan(String mKode, String mNama, int mHakAkses) {
        this.mKode = mKode;
        this.mNama = mNama;
        this.mHakAkses = mHakAkses;
        buatParameterTabelJabatan();
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
    public void save(final UpdateOnUIThreadWrite<Jabatan> metodeUIAfterSave){
        //Todo: Definisikan Proses Save
        final Jabatan jabatan=this;
        mDatabase.save(NAMA_TABEL, mPasanganKolomNilai, new DatabaseClass.AfterGetResponseListenerWrite() {
            @Override
            public void updateUIThread(Boolean response) {
                metodeUIAfterSave.updateOnUIThread(jabatan);
            }
        });
    }

    public void update(final UpdateOnUIThreadWrite<Jabatan> metodeUIAfterUpdate){
        //Todo: Definisikan Proses Update
        final Jabatan jabatan=this;
        mDatabase.update(NAMA_TABEL, mPasanganKolomNilai, mPasanganKolomNilai.get(0).getmName() + "="
                + mPasanganKolomNilai.get(0).getmValue(), new DatabaseClass.AfterGetResponseListenerWrite() {
            @Override
            public void updateUIThread(Boolean response) {
                metodeUIAfterUpdate.updateOnUIThread(jabatan);
            }
        });
    }

    public static void fetch(final UpdateOnUIThreadRead<Jabatan> metodeUIAfterFetch){
        //Todo: Definisikan Proses Load Data
        DatabaseClass<Jabatan> databaseClass= new DatabaseClass<>();
        databaseClass.fetchAll(NAMA_TABEL, new DatabaseClass.AfterGetResponseListenerRead<Jabatan>() {
            @Override
            public ArrayList<Jabatan> afterGetResponse(String responseJSON) {
                //Todo: Parsing JSON Jabatan
                return null;
            }

            @Override
            public void updateUIThread(ArrayList<Jabatan> response) {
                metodeUIAfterFetch.updateOnUIThread(response);
            }
        });

    }

    public void delete(final UpdateOnUIThreadWrite<Jabatan> metodeUIAfterDelete){
        //Todo: Definisikan Proses Delete
        final Jabatan jabatan=this;
        mDatabase.delete(NAMA_TABEL, mPasanganKolomNilai.get(0).getmName() + "="
                + mPasanganKolomNilai.get(0).getmValue(), new DatabaseClass.AfterGetResponseListenerWrite() {
            @Override
            public void updateUIThread(Boolean response) {
                metodeUIAfterDelete.updateOnUIThread(jabatan);
            }
        });
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
