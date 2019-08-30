package com.example.android.diamondcell;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Sales implements Parcelable
{
    private String mKode;
    private Date mTglMasuk;
    private String mNama;
    private String mAlamat;
    private String mTelp;
    private String mKodeJabatan;
    private JenisKelamin mJenisKelamin;
    private Agama mAgama;
    private Date mTanggalLahir;
    private String mTempatLahir;
    private String mEmail;
    private boolean mStatusAktif;
    private String mFoto;
    private ArrayList<KeyValuePair> mPasanganKolomNilai;
    private final static String NAMATABEL="tbSales";
    private void buatParameterTabelSales(){
        mPasanganKolomNilai= new ArrayList<>();
        mPasanganKolomNilai.add(new KeyValuePair("kode",mKode));
        mPasanganKolomNilai.add(new KeyValuePair("tgl_masuk",formatDateToMysqlDate(mTglMasuk)));
        mPasanganKolomNilai.add(new KeyValuePair("nama",mNama));
        mPasanganKolomNilai.add(new KeyValuePair("alamat",mAlamat));
        mPasanganKolomNilai.add(new KeyValuePair("telp",mTelp));
        mPasanganKolomNilai.add(new KeyValuePair("kode_jabatan",mKodeJabatan));
        mPasanganKolomNilai.add(new KeyValuePair("jenis_kelamin",getmJenisKelaminAsString()));
        mPasanganKolomNilai.add(new KeyValuePair("agama",getmAgamaAsString()));
        mPasanganKolomNilai.add(new KeyValuePair("tgl_lahir",formatDateToMysqlDate(mTanggalLahir)));
        mPasanganKolomNilai.add(new KeyValuePair("email",mEmail));
        mPasanganKolomNilai.add(new KeyValuePair("status",String.valueOf(mStatusAktif)));
        mPasanganKolomNilai.add(new KeyValuePair("foto",String.valueOf(mFoto)));

    }
    private String formatDateToMysqlDate(Date date){
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        return simpleDateFormat.format(date);
    }
    protected Sales(Parcel in) {
        mKode = in.readString();
        mTglMasuk= new Date(in.readLong());
        mNama = in.readString();
        mAlamat = in.readString();
        mTelp = in.readString();
        mKodeJabatan = in.readString();
        int jk= in.readByte();
        switch (jk){
            case 0: mJenisKelamin=JenisKelamin.PRIA;
                    break;
            case 1: mJenisKelamin=JenisKelamin.WANITA;
                    break;
        }
        int agm= in.readByte();
        switch (agm){
            case 0:
                mAgama=Agama.HINDU;
                break;
            case 1:
                mAgama=Agama.ISLAM;
                break;
            case 2:
                mAgama=Agama.BUDDHA;
                break;
            case 3:
                mAgama=Agama.KATOLIK;
                break;
            case 4:
                mAgama=Agama.KRISTEN;
                break;
            case 5:
                mAgama=Agama.KONGHUCU;
                break;
            case 6:
                mAgama=Agama.LAINNYA;
                break;
        }
        mTempatLahir = in.readString();
        mTanggalLahir=new Date(in.readLong());
        mEmail = in.readString();
        mStatusAktif = in.readByte() != 0;
        mFoto = in.readString();
        buatParameterTabelSales();
    }

    public static final Creator<Sales> CREATOR = new Creator<Sales>() {
        @Override
        public Sales createFromParcel(Parcel in) {
            return new Sales(in);
        }

        @Override
        public Sales[] newArray(int size) {
            return new Sales[size];
        }
    };

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
        parcel.writeString(mKodeJabatan);
        switch (mJenisKelamin){
            case PRIA:
                parcel.writeByte((byte) 0);
                break;
            case WANITA:
                parcel.writeByte((byte) 1);
                break;
        }
        switch (mAgama){
            case HINDU:
                parcel.writeByte((byte) 0);
                break;
            case ISLAM:
                parcel.writeByte((byte) 1);
                break;
            case BUDDHA:
                parcel.writeByte((byte) 2);
                break;
            case KATOLIK:
                parcel.writeByte((byte) 3);
                break;
            case KRISTEN:
                parcel.writeByte((byte) 4);
                break;
            case KONGHUCU:
                parcel.writeByte((byte) 5);
                break;
            case LAINNYA:
                parcel.writeByte((byte) 6);
                break;
        }
        parcel.writeString(mTempatLahir);
        parcel.writeLong(mTanggalLahir.getTime());
        parcel.writeString(mEmail);
        parcel.writeByte((byte) (mStatusAktif ? 1 : 0));
        parcel.writeString(mFoto);
    }
    public Sales(String mKode){
        this.mKode=mKode;
        buatParameterTabelSales();
        //Todo: Dapatkan data dari database berdasarkan Kode Sales
    }
    public Sales(String mKode, Date mTglMasuk, String mNama, String mAlamat, String mTelp, String mKodeJabatan,
                 JenisKelamin mJenisKelamin, Agama mAgama, Date mTanggalLahir, String mTempatLahir, String mEmail,
                 boolean mStatusAktif, String mFoto) {
        this.mKode = mKode;
        this.mTglMasuk = mTglMasuk;
        this.mNama = mNama;
        this.mAlamat = mAlamat;
        this.mTelp = mTelp;
        this.mKodeJabatan = mKodeJabatan;
        this.mJenisKelamin = mJenisKelamin;
        this.mAgama = mAgama;
        this.mTanggalLahir = mTanggalLahir;
        this.mTempatLahir = mTempatLahir;
        this.mEmail = mEmail;
        this.mStatusAktif = mStatusAktif;
        this.mFoto = mFoto;
        buatParameterTabelSales();
    }
    private Sales getInstance(){
        return this;
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

    public String getmKodeJabatan() {
        return mKodeJabatan;
    }

    public void setmKodeJabatan(String mKodeJabatan) {
        this.mKodeJabatan = mKodeJabatan;
    }

    public JenisKelamin getmJenisKelamin() {
        return mJenisKelamin;
    }

    public void setmJenisKelamin(JenisKelamin mJenisKelamin) {
        this.mJenisKelamin = mJenisKelamin;
    }

    public Agama getmAgama() {
        return mAgama;
    }

    public void setmAgama(Agama mAgama) {
        this.mAgama = mAgama;
    }

    public Date getmTanggalLahir() {
        return mTanggalLahir;
    }

    public void setmTanggalLahir(Date mTanggalLahir) {
        this.mTanggalLahir = mTanggalLahir;
    }

    public String getmTempatLahir() {
        return mTempatLahir;
    }

    public void setmTempatLahir(String mTempatLahir) {
        this.mTempatLahir = mTempatLahir;
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

    public String getmJenisKelaminAsString(){
        if (mJenisKelamin==JenisKelamin.PRIA){
            return "Pria";
        }else if (mJenisKelamin==JenisKelamin.WANITA){
            return "Wanita";
        }else return "Unkown";
    }

    public String getmAgamaAsString(){
        switch (mAgama){
            case HINDU:
                return "Hindu";
            case ISLAM:
                return "Islam";
            case BUDDHA:
                return "Buddha";
            case KATOLIK:
                return "Katolik";
            case KRISTEN:
                return "Kristen";
            case KONGHUCU:
                return "Konghucu";
            case LAINNYA:
                return "Lainnya";
            default:
                return "Unkown";
        }
    }
    public String getmStatusAktifAsString(){
        if (mStatusAktif){
            return "Aktif";
        }else if (!mStatusAktif){
            return "Tidak Aktif";
        }else return "Unkown";
    }
    public String getmTanggalLahirAsString(){
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return dateFormat.format(mTanggalLahir);
    }
    public String getmTglMasukAsString(){
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return dateFormat.format(mTglMasuk);
    }


    public Jabatan getJabatan(){
        //Todo: Definisikan Proses Kueri Untuk Mendapatkan Jabatan Berdasarkan Kode Jabatan
        return new Jabatan("mKode","namaJabatan",2);
    }

    public void save(final UpdateOnUIThreadWrite<Sales> metodeUISetelahSave){
        DatabaseClass<Sales> databaseClass= new DatabaseClass<>();
        databaseClass.save(NAMATABEL, mPasanganKolomNilai, new DatabaseClass.AfterGetResponseListenerWrite() {
            @Override
            public void updateUIThread(Boolean response) {
                if (response){
                    metodeUISetelahSave.updateOnUIThread(getInstance());
                }
            }
        });
    }

    public void update(final UpdateOnUIThreadWrite<Sales> metodeUISetelahUpdate){
        DatabaseClass<Sales> databaseClass= new DatabaseClass<>();
        databaseClass.update(NAMATABEL,mPasanganKolomNilai,mPasanganKolomNilai.get(0).getmName()+"="+
                mPasanganKolomNilai.get(0).getmValue(), new DatabaseClass.AfterGetResponseListenerWrite() {
            @Override
            public void updateUIThread(Boolean response) {
                metodeUISetelahUpdate.updateOnUIThread(getInstance());
            }
        });

    }

    public static void fetch(final UpdateOnUIThreadRead<Sales> metodeUISetelahFetch){
        //Todo: Definisikan Proses Load Data
        DatabaseClass<Sales> databaseClass= new DatabaseClass<>();
        databaseClass.fetchAll(NAMATABEL, new DatabaseClass.AfterGetResponseListenerRead<Sales>() {
            @Override
            public ArrayList<Sales> afterGetResponse(String responseJSON) {
                //Todo: Definisikan Proses Parsing JSON;
                return null;
            }

            @Override
            public void updateUIThread(ArrayList<Sales> response) {
                metodeUISetelahFetch.updateOnUIThread(response);
            }
        });
    }

    public void delete(final UpdateOnUIThreadWrite<Sales> metodeUISetelahDelete){
        //Todo: Definisikan Proses Delete
        DatabaseClass<Sales> databaseClass= new DatabaseClass<>();
        databaseClass.delete(NAMATABEL,mPasanganKolomNilai.get(0).getmName()+"="+
                mPasanganKolomNilai.get(0).getmValue(), new DatabaseClass.AfterGetResponseListenerWrite() {
            @Override
            public void updateUIThread(Boolean response) {
                metodeUISetelahDelete.updateOnUIThread(getInstance());
            }
        });
    }


}
