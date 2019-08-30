package com.example.android.diamondcell;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Pelanggan implements Parcelable {
    private String mKode;
    private Date mTglMasuk;
    private String mNama;
    private String mAlamat;
    private String mTelp;
    private String mHandphone;
    private String mEmail;
    private boolean mStatusAktif;
    private String mFoto;
    private ArrayList<KeyValuePair> mPasanganKolomNilai;
    private final static String NAMATABEL="tbPelanggan";
    private DatabaseClass<Pelanggan> mDatabase;
    public Pelanggan(String mKode) {
        this.mKode = mKode;
        //Todo: Dapatkan data pelanggan dari database berdasarkan kode
        buatParameterTabelPelanggan();
    }
    private Pelanggan getInstance(){
        return this;
    }
    private void buatParameterTabelPelanggan(){
        mPasanganKolomNilai= new ArrayList<>();
        mPasanganKolomNilai.add(new KeyValuePair("kode",mKode));
        mPasanganKolomNilai.add(new KeyValuePair("tgl_masuk",formatDateToMysqlDate(mTglMasuk)));
        mPasanganKolomNilai.add(new KeyValuePair("nama",mNama));
        mPasanganKolomNilai.add(new KeyValuePair("alamat",mAlamat));
        mPasanganKolomNilai.add(new KeyValuePair("telp",mTelp));
        mPasanganKolomNilai.add(new KeyValuePair("handphone",mHandphone));;
        mPasanganKolomNilai.add(new KeyValuePair("email",mEmail));
        mPasanganKolomNilai.add(new KeyValuePair("status",getmStatusAktifAsString()));
        mPasanganKolomNilai.add(new KeyValuePair("foto",String.valueOf(mFoto)));
        mDatabase=new DatabaseClass<>();

    }
    private String formatDateToMysqlDate(Date date){
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        return simpleDateFormat.format(date);
    }
    public Pelanggan(String mKode, Date mTglMasuk, String mNama, String mAlamat, String mTelp, String mHandphone, String mEmail, boolean mStatusAktif, String mFoto) {
        this.mKode = mKode;
        this.mTglMasuk = mTglMasuk;
        this.mNama = mNama;
        this.mAlamat = mAlamat;
        this.mTelp = mTelp;
        this.mHandphone = mHandphone;
        this.mEmail = mEmail;
        this.mStatusAktif = mStatusAktif;
        this.mFoto = mFoto;
        buatParameterTabelPelanggan();
    }

    protected Pelanggan(Parcel in) {
        mKode = in.readString();
        mTglMasuk=new Date(in.readLong());
        mNama = in.readString();
        mAlamat = in.readString();
        mTelp = in.readString();
        mHandphone = in.readString();
        mEmail = in.readString();
        mStatusAktif = in.readByte() != 0;
        mFoto = in.readString();
    }

    public static final Creator<Pelanggan> CREATOR = new Creator<Pelanggan>() {
        @Override
        public Pelanggan createFromParcel(Parcel in) {
            return new Pelanggan(in);
        }

        @Override
        public Pelanggan[] newArray(int size) {
            return new Pelanggan[size];
        }
    };

    public String getmKode() {
        return mKode;
    }

    public void setmKode(String mKode) {
        this.mKode = mKode;
    }

    public Date getmTglMasuk() {
        return mTglMasuk;
    }

    public void setmTglMasuk(Date mTglMasuk) {
        this.mTglMasuk = mTglMasuk;
    }

    public String getmNama() {
        return mNama;
    }

    public void setmNama(String mNama) {
        this.mNama = mNama;
    }

    public String getmAlamat() {
        return mAlamat;
    }

    public void setmAlamat(String mAlamat) {
        this.mAlamat = mAlamat;
    }

    public String getmTelp() {
        return mTelp;
    }

    public void setmTelp(String mTelp) {
        this.mTelp = mTelp;
    }

    public String getmHandphone() {
        return mHandphone;
    }

    public void setmHandphone(String mHandphone) {
        this.mHandphone = mHandphone;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public boolean ismStatusAktif() {
        return mStatusAktif;
    }

    public void setmStatusAktif(boolean mStatusAktif) {
        this.mStatusAktif = mStatusAktif;
    }

    public String getmFoto() {
        return mFoto;
    }

    public void setmFoto(String mFoto) {
        this.mFoto = mFoto;
    }

    public String getTglMasukAsString(){
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return dateFormat.format(mTglMasuk);
    }

    public String getmStatusAktifAsString(){
        if (mStatusAktif){
            return "Aktif";
        }else if (!mStatusAktif){
            return "Tidak Aktif";
        }else return "Unkown";
    }
    public void save(final UpdateOnUIThreadWrite<Pelanggan> metodeAfterSave){
        //Todo: Definisikan Proses Save
        mDatabase.save(NAMATABEL, mPasanganKolomNilai, new DatabaseClass.AfterGetResponseListenerWrite() {
            @Override
            public void updateUIThread(Boolean response) {
                metodeAfterSave.updateOnUIThread(getInstance());
            }
        });

    }

    public void update(final UpdateOnUIThreadWrite<Pelanggan> metodeAfterUpdate){
        //Todo: Definisikan Proses Update
        mDatabase.update(NAMATABEL, mPasanganKolomNilai, mPasanganKolomNilai.get(0).getmName() + "="
                + mPasanganKolomNilai.get(0).getmValue(), new DatabaseClass.AfterGetResponseListenerWrite() {
            @Override
            public void updateUIThread(Boolean response) {
                metodeAfterUpdate.updateOnUIThread(getInstance());
            }
        });
    }

    public static void fetch(final UpdateOnUIThreadRead<Pelanggan> metodeAfterFetch){
        //Todo: Definisikan Proses Load Data
        final DatabaseClass<Pelanggan> pelangganDatabaseClass= new DatabaseClass<>();
        pelangganDatabaseClass.fetchAll(NAMATABEL, new DatabaseClass.AfterGetResponseListenerRead<Pelanggan>() {
            @Override
            public ArrayList<Pelanggan> afterGetResponse(String responseJSON) {
                //Todo: parsing JSON
                return null;
            }

            @Override
            public void updateUIThread(ArrayList<Pelanggan> response) {
                metodeAfterFetch.updateOnUIThread(response);
            }
        });
    }

    public void delete(final UpdateOnUIThreadWrite<Pelanggan> metodeAfterDelete){
        //Todo: Definisikan Proses Delete
        mDatabase.delete(NAMATABEL, mPasanganKolomNilai.get(0).getmName() + "="
                + mPasanganKolomNilai.get(0).getmValue(), new DatabaseClass.AfterGetResponseListenerWrite() {
            @Override
            public void updateUIThread(Boolean response) {
                metodeAfterDelete.updateOnUIThread(getInstance());
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
        parcel.writeLong(mTglMasuk.getTime());
        parcel.writeString(mNama);
        parcel.writeString(mAlamat);
        parcel.writeString(mTelp);
        parcel.writeString(mHandphone);
        parcel.writeString(mEmail);
        parcel.writeByte((byte) (mStatusAktif ? 1 : 0));
        parcel.writeString(mFoto);
    }
}
